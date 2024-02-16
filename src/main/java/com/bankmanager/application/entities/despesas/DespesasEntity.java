package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "despesas")
public class DespesasEntity extends AbstractEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    private UsuariosEntity usuario;

    @OneToMany(mappedBy = "despesa", fetch = FetchType.EAGER)
    private Collection<TransacoesEntity> transacoes;

    public boolean isPago() {
        var maxDate = transacoes.stream()
                .map(TransacoesEntity::getMesReferencia)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        var result = false;
        if (Objects.nonNull(maxDate)) {
            var lastMonthPaid = LocalDateTimeHelper.getMonthStr(maxDate);
            var thisMonth = LocalDateTimeHelper.getMonthStr(LocalDateTime.now());
            result = lastMonthPaid.equals(thisMonth);
        }

        return result;
    }
}

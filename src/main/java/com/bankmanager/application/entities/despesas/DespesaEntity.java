package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.enums.despesas.TipoDespesaEnum;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import com.bankmanager.application.models.despesas.TrasacaoModel;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "despesas")
public class DespesaEntity extends AbstractEntity {

    @Column(nullable = false)
    private String nome;

    @Builder.Default
    @Column(nullable = false)
    private Double valor = 0D;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDespesaEnum tipo = TipoDespesaEnum.MENSAL;

    @ManyToOne(optional = false)
    private UsuariosEntity usuario;

    @OneToMany(mappedBy = "despesa", fetch = FetchType.EAGER)
    private Collection<TransacaoEntity> transacoes;

    public boolean isPago() {
        var maxDate = transacoes.stream()
                .map(TransacaoEntity::getDataReferencia)
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

    public boolean isRecorrente() {
        return TipoDespesaEnum.RECORRENTE.equals(this.getTipo());
    }

    public boolean isMensal() {
        return TipoDespesaEnum.MENSAL.equals(this.getTipo());
    }

    public boolean isAnual() {
        return TipoDespesaEnum.ANUAL.equals(this.getTipo());
    }
}

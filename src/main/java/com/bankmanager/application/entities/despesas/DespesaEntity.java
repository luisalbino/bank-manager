package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.enums.despesas.TipoDespesaEnum;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import jakarta.persistence.*;
import lombok.*;

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
        return isAnual() ? isPagoAnual() : this.isPagoMensal();
    }

    public boolean isPagoAnual() {
        var resultado = false;
        var maiorDataPagamento = getMaiorDataPagamento();
        if (Objects.nonNull(maiorDataPagamento)) {
            var lastMonthPaid = LocalDateTimeHelper.getYearStr(maiorDataPagamento);
            var thisMonth = LocalDateTimeHelper.getYearStr(LocalDateTime.now());
            resultado = lastMonthPaid.equals(thisMonth);
        }

        return resultado;
    }

    public boolean isPagoMensal() {
        var maiorDataPagamento = getMaiorDataPagamento();

        var result = false;
        if (Objects.nonNull(maiorDataPagamento)) {
            var lastMonthPaid = LocalDateTimeHelper.getMonthStr(maiorDataPagamento);
            var thisMonth = LocalDateTimeHelper.getMonthStr(LocalDateTime.now());
            result = lastMonthPaid.equals(thisMonth);
        }

        return result;
    }

    public LocalDateTime getMaiorDataPagamento() {
        return transacoes.stream()
                .map(TransacaoEntity::getDataReferencia)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    public boolean isNotRecorrente() {
        return !TipoDespesaEnum.RECORRENTE.equals(this.getTipo());
    }

    public boolean isAnual() {
        return TipoDespesaEnum.ANUAL.equals(this.getTipo());
    }
}

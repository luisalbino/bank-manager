package com.bankmanager.application.models.despesas.transacoes;

import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.ConvertHelper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class CadastroTransacaoModel {
    private Long idTrasacao;

    private Double valor;

    private LocalDate dataPagamento;
    private LocalDate dataReferencia;

    public CadastroTransacaoModel(DespesaEntity despesa) {
        this.valor = ConvertHelper.toDouble(despesa.getValor(), 0D);
        this.dataPagamento = LocalDate.now();
        this.dataReferencia = LocalDate.now();
    }

    public CadastroTransacaoModel(TrasacaoModel trasacaoModel) {
        this.idTrasacao = trasacaoModel.getId();

        this.valor = ConvertHelper.toDouble(trasacaoModel.getValor(), 0D);
        this.dataPagamento = trasacaoModel.getDataPagamento().toLocalDate();

        if (Objects.nonNull(trasacaoModel.getDataReferencia())) {
            this.dataReferencia = trasacaoModel.getDataReferencia().toLocalDate();
        }
    }
}

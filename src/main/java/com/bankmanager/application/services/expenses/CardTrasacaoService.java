package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.entities.despesas.TransacaoEntity;
import com.bankmanager.application.enums.despesas.transacoes.TipoPerformanceEnum;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.CurrencyHelper;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import com.bankmanager.application.models.despesas.transacoes.CardTrasacaoModel;
import com.bankmanager.application.models.despesas.transacoes.TrasacaoModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Service
public class CardTrasacaoService {

    private final TransacaoService transacaoService;

    public CardTrasacaoService(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public Collection<TrasacaoModel> getModels(DespesaEntity despesa) {
        var result = new ArrayList<TrasacaoModel>();

        if (CollectionUtils.isNotEmpty(despesa.getTransacoes())) {
            Double valorUltimoPagamento = null;

            var trasacoes = despesa.getTransacoes().stream().sorted(Comparator.comparing(TransacaoEntity::getDataPagamento)).toList();
            for (var trasacao : trasacoes)  {
                var trasacaoModel = new TrasacaoModel();
                trasacaoModel.setId(trasacao.getId());
                trasacaoModel.setValor(trasacao.getValor());
                trasacaoModel.setValorStr(CurrencyHelper.getMoney(trasacao.getValor()));

                if (Objects.nonNull(trasacao.getDataReferencia())) {
                    var dataCompetencia = LocalDateTimeHelper.formatMMYYYY(trasacao.getDataReferencia());
                    if (despesa.isAnual()) {
                        dataCompetencia = LocalDateTimeHelper.formatYYYY(trasacao.getDataReferencia());
                    }

                    trasacaoModel.setDataCompetenciaStr(dataCompetencia);
                    trasacaoModel.setDataReferencia(trasacao.getDataReferencia());
                }

                trasacaoModel.setDataPagamento(trasacao.getDataPagamento());
                trasacaoModel.setDataPagamentoStr(LocalDateTimeHelper.formatDDMMYYYY(trasacao.getDataPagamento()));

                if (Objects.isNull(valorUltimoPagamento)) {
                    valorUltimoPagamento = trasacao.getValor();
                    trasacaoModel.setPerformance(CurrencyHelper.getPercentual(0D));
                } else {
                    var valorPagamentoAtual = trasacao.getValor();

                    var isPagamentoAtualPior = valorUltimoPagamento.compareTo(valorPagamentoAtual) < 0;
                    var isPagamentoNeutro = valorUltimoPagamento.compareTo(valorPagamentoAtual) == 0;

                    var divisor = isPagamentoAtualPior ? valorUltimoPagamento : valorPagamentoAtual;
                    var dividendo = isPagamentoAtualPior ? valorPagamentoAtual : valorUltimoPagamento;
                    if (dividendo.compareTo(0D) == 0) dividendo = 1D;

                    trasacaoModel.setPerformance(CurrencyHelper.getPercentual(100 - ((divisor / dividendo) * 100)));

                    if (!isPagamentoNeutro) {
                        trasacaoModel.setTipoPerformance(isPagamentoAtualPior ? TipoPerformanceEnum.NEGATIVO : TipoPerformanceEnum.POSITIVO);
                    }
                }

                result.add(trasacaoModel);
            }
        }

        return result.stream().sorted(Comparator.comparing(TrasacaoModel::getDataPagamento).reversed()).toList();
    }

    public void pagarOrEditar(DespesaEntity despesa, CardTrasacaoModel cardTrasacaoModel) {
        var trasacao = this.transacaoService.getOrNew(cardTrasacaoModel.getIdTrasacao());
        trasacao.setDescricao("Despesa " + despesa.getNome());
        trasacao.setDataPagamento(cardTrasacaoModel.getDataPagamento().atStartOfDay());
        trasacao.setDataReferencia(cardTrasacaoModel.getDataReferencia().atStartOfDay());
        trasacao.setValor(cardTrasacaoModel.getValor());
        trasacao.setDespesa(despesa);

        transacaoService.save(trasacao);
    }

    public String getFooterValue(Collection<TrasacaoModel> cardsExpenses) {
        var totalValue = ConvertHelper.toDouble(cardsExpenses.stream().mapToDouble(TrasacaoModel::getValor).sum(), 0D);
        return CurrencyHelper.getMoney(totalValue);
    }
}

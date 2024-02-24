package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.entities.despesas.TransacaoEntity;
import com.bankmanager.application.enums.despesas.transacoes.TipoPerformanceEnum;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.CurrencyHelper;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import com.bankmanager.application.models.despesas.TrasacaoModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Service
public class CardTrasacaoService {

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
                    trasacaoModel.setDataCompetencia(LocalDateTimeHelper.getDateStr(trasacao.getDataReferencia()));
                }

                trasacaoModel.setDataPagamento(trasacao.getDataPagamento());
                trasacaoModel.setDataPagamentoStr(LocalDateTimeHelper.getDateStr(trasacao.getDataPagamento()));

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

    public String getFooterValue(Collection<TrasacaoModel> cardsExpenses) {
        var totalValue = ConvertHelper.toDouble(cardsExpenses.stream().mapToDouble(TrasacaoModel::getValor).sum(), 0D);
        return CurrencyHelper.getMoney(totalValue);
    }
}

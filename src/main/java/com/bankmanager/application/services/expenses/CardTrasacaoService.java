package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.CurrencyHelper;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import com.bankmanager.application.models.despesas.CardDespesaModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class CardTrasacaoService {

    public Collection<CardDespesaModel> getModels(DespesasEntity expense) {
        var result = new ArrayList<CardDespesaModel>();

        if (CollectionUtils.isNotEmpty(expense.getTransacoes())) {
            for (var cashFlow : expense.getTransacoes())  {
                var cardExense = new CardDespesaModel();
                cardExense.setIdExpense(cashFlow.getId());
                cardExense.setValueDisplay(CurrencyHelper.convert(cashFlow.getValor()));
                cardExense.setValue(ConvertHelper.toDouble(cashFlow.getValor(), 0D));

                if (Objects.nonNull(cashFlow.getDataReferencia())) {
                    cardExense.setCompetencyDate(LocalDateTimeHelper.getDateStr(cashFlow.getDataReferencia()));
                }
                cardExense.setPaymentDate(LocalDateTimeHelper.getDateStr(cashFlow.getDataPagamento()));
                result.add(cardExense);
            }
        }

        return result;
    }

    public String getFooterValue(Collection<CardDespesaModel> cardsExpenses) {
        var totalValue = ConvertHelper.toDouble(cardsExpenses.stream().mapToDouble(CardDespesaModel::getValue).sum(), 0D);
        return CurrencyHelper.convert(totalValue);
    }
}

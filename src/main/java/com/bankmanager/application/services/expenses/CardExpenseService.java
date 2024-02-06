package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.CurrencyHelper;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import com.bankmanager.application.models.expenses.carousel.CardExpenseModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class CardExpenseService {

    private final static DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Collection<CardExpenseModel> getModels(ExpenseEntity expense) {
        var result = new ArrayList<CardExpenseModel>();

        if (CollectionUtils.isNotEmpty(expense.getCashFlows())) {
            for (var cashFlow : expense.getCashFlows())  {
                var cardExense = new CardExpenseModel();
                cardExense.setIdExpense(cashFlow.getId());
                cardExense.setValueDisplay(CurrencyHelper.convert(cashFlow.getValue()));
                cardExense.setValue(ConvertHelper.toDouble(cashFlow.getValue(), 0D));

                if (Objects.nonNull(cashFlow.getCompetencyData())) {
                    cardExense.setCompetencyDate(LocalDateTimeHelper.getDateStr(cashFlow.getCompetencyData()));
                }
                cardExense.setPaymentDate(LocalDateTimeHelper.getDateStr(cashFlow.getOperationDate()));
                result.add(cardExense);
            }
        }

        return result;
    }

    public String getFooterValue(Collection<CardExpenseModel> cardsExpenses) {
        var totalValue = ConvertHelper.toDouble(cardsExpenses.stream().mapToDouble(CardExpenseModel::getValue).sum(), 0D);
        return CurrencyHelper.convert(totalValue);
    }
}

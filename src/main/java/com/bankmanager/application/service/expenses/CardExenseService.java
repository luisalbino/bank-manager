package com.bankmanager.application.service.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.models.expenses.carousel.CardExenseModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CardExenseService {

    private final static DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public Collection<CardExenseModel> getModels(ExpenseEntity expense) {
        var result = new ArrayList<CardExenseModel>();

        if (CollectionUtils.isNotEmpty(expense.getCashFlows())) {
            for (var cashFlow : expense.getCashFlows())  {
                var cardExense = new CardExenseModel();
                cardExense.setValue(ConvertHelper.get(cashFlow.getValue(), 0D));
                cardExense.setDate(cashFlow.getOperationDate().format(ISO_FORMATTER));
                result.add(cardExense);
            }
        }

        return result;
    }
}

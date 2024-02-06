package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.cash.CashFlowEntity;
import com.bankmanager.application.repositories.expenses.CashFlowRepository;
import com.bankmanager.application.services.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CashFlowService extends AbstractService<CashFlowEntity, CashFlowRepository> {

    protected CashFlowService(CashFlowRepository repository) {
        super(repository);
    }

    public void update(Long id, LocalDate competencyDate) {
        var cashFlow = this.repository.findById(id).orElse(null);
        if (Objects.nonNull(cashFlow)) {
            cashFlow.setCompetencyData(competencyDate.atStartOfDay());
            this.save(cashFlow);
        }
    }
}

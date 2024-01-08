package com.bankmanager.application.service.expenses;

import com.bankmanager.application.entities.cash.CashFlowEntity;
import com.bankmanager.application.repositories.expenses.CashFlowRepository;
import com.bankmanager.application.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class CashFlowService extends AbstractService<CashFlowEntity, CashFlowRepository> {

    protected CashFlowService(CashFlowRepository repository) {
        super(repository);
    }
}

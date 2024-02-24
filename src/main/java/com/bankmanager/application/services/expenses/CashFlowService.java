package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.TransacaoEntity;
import com.bankmanager.application.repositories.desepesas.TransacoesRepository;
import com.bankmanager.application.services.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CashFlowService extends AbstractService<TransacaoEntity, TransacoesRepository> {

    protected CashFlowService(TransacoesRepository repository) {
        super(repository);
    }

    public void update(Long id, LocalDate competencyDate) {
        var cashFlow = this.repository.findById(id).orElse(null);
        if (Objects.nonNull(cashFlow)) {
            cashFlow.setDataReferencia(competencyDate.atStartOfDay());
            this.save(cashFlow);
        }
    }
}

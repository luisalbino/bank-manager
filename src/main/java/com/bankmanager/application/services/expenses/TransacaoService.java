package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.TransacaoEntity;
import com.bankmanager.application.repositories.desepesas.TransacoesRepository;
import com.bankmanager.application.services.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class TransacaoService extends AbstractService<TransacaoEntity, TransacoesRepository> {

    protected TransacaoService(TransacoesRepository repository) {
        super(repository);
    }

    public void update(Long id, LocalDate dataCompetencia) {
        var cashFlow = this.repository.findById(id).orElse(null);
        if (Objects.nonNull(cashFlow)) {
            cashFlow.setDataReferencia(dataCompetencia.atStartOfDay());
            this.save(cashFlow);
        }
    }

    public TransacaoEntity getOrNew(Long id) {
        return Objects.nonNull(id) ? this.repository.findById(id).orElse(new TransacaoEntity()) : new TransacaoEntity();
    }
}

package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.entities.despesas.TransacoesEntity;
import com.bankmanager.application.repositories.desepesas.DespesasRepository;
import com.bankmanager.application.services.AbstractService;
import com.bankmanager.application.services.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

@Service
public class DespesaService extends AbstractService<DespesasEntity, DespesasRepository> {

    private final UserService userService;
    private final CashFlowService cashFlowService;

    protected DespesaService(DespesasRepository repository, UserService userService, CashFlowService cashFlowService) {
        super(repository);
        this.userService = userService;
        this.cashFlowService = cashFlowService;
    }

    public void create(DespesasEntity expense) {
        expense.setUsuario(userService.getLoggedUser());
        save(expense);
    }

    public void pay(DespesasEntity expense, LocalDate paymentDate, LocalDate competencyDate, Double value) {
        var cashFlow = new TransacoesEntity();
        cashFlow.setDescricao("Despesa " + expense.getNome());
        cashFlow.setDataPagamento(paymentDate.atStartOfDay());
        cashFlow.setMesReferencia(competencyDate.atStartOfDay());
        cashFlow.setValor(value);
        cashFlow.setDespesa(expense);
        cashFlowService.save(cashFlow);

        this.save(expense);
    }

    @Override
    public Collection<DespesasEntity> getAll() {
        return repository.getByUsuario(userService.getLoggedUser()).stream().sorted(Comparator.comparingLong(DespesasEntity::getId)).toList();
    }
}

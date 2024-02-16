package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.FluxoCaixaEntity;
import com.bankmanager.application.entities.despesas.DesepesaEntity;
import com.bankmanager.application.enums.cash.FlowTypeEnum;
import com.bankmanager.application.repositories.expenses.DespesaRepository;
import com.bankmanager.application.services.AbstractService;
import com.bankmanager.application.services.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

@Service
public class DespesaService extends AbstractService<DesepesaEntity, DespesaRepository> {

    private final UserService userService;
    private final CashFlowService cashFlowService;

    protected DespesaService(DespesaRepository repository, UserService userService, CashFlowService cashFlowService) {
        super(repository);
        this.userService = userService;
        this.cashFlowService = cashFlowService;
    }

    public void create(DesepesaEntity expense) {
        expense.setUser(userService.getLoggedUser());
        save(expense);
    }

    public void pay(DesepesaEntity expense, LocalDate paymentDate, LocalDate competencyDate, Double value) {
        var cashFlow = new FluxoCaixaEntity();
        cashFlow.setDescription("Despesa " + expense.getName());
        cashFlow.setFlow(FlowTypeEnum.OUTFLOW);
        cashFlow.setOperationDate(paymentDate.atStartOfDay());
        cashFlow.setCompetencyData(competencyDate.atStartOfDay());
        cashFlow.setValue(value);
        cashFlow.setExpense(expense);
        cashFlowService.save(cashFlow);

        this.save(expense);
    }

    @Override
    public Collection<DesepesaEntity> getAll() {
        return repository.getByUser(userService.getLoggedUser()).stream().sorted(Comparator.comparingLong(DesepesaEntity::getId)).toList();
    }
}

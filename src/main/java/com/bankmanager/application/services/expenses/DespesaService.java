package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.entities.despesas.TransacaoEntity;
import com.bankmanager.application.repositories.desepesas.DespesasRepository;
import com.bankmanager.application.services.AbstractService;
import com.bankmanager.application.services.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

@Service
public class DespesaService extends AbstractService<DespesaEntity, DespesasRepository> {

    private final UserService userService;
    private final TransacaoService transacaoService;

    protected DespesaService(DespesasRepository repository, UserService userService, TransacaoService transacaoService) {
        super(repository);
        this.userService = userService;
        this.transacaoService = transacaoService;
    }

    public void create(DespesaEntity expense) {
        expense.setUsuario(userService.getLoggedUser());
        save(expense);
    }

    public void pay(DespesaEntity expense, LocalDate paymentDate, LocalDate competencyDate, Double value) {
        var cashFlow = new TransacaoEntity();
        cashFlow.setDescricao("Despesa " + expense.getNome());
        cashFlow.setDataPagamento(paymentDate.atStartOfDay());
        cashFlow.setDataReferencia(competencyDate.atStartOfDay());
        cashFlow.setValor(value);
        cashFlow.setDespesa(expense);
        transacaoService.save(cashFlow);

        this.save(expense);
    }

    @Override
    public Collection<DespesaEntity> getAll() {
        return repository.getByUsuario(userService.getLoggedUser()).stream().sorted(Comparator.comparingLong(DespesaEntity::getId)).toList();
    }
}

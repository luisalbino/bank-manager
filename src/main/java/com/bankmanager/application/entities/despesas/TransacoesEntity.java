package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "trasacoes")
public class TransacoesEntity extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    private LocalDateTime mesReferencia;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne
    private DespesasEntity despesa;
}

package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.despesas.DesepesaEntity;
import com.bankmanager.application.enums.cash.FlowTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "cash_flow_entity")
public class FluxoCaixaEntity extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlowTypeEnum flow;

    @Column(nullable = false)
    private LocalDateTime operationDate;

    private LocalDateTime competencyData;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    private DesepesaEntity expense;
}

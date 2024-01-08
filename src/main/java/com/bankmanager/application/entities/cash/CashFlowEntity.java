package com.bankmanager.application.entities.cash;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.enums.cash.FlowTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
public class CashFlowEntity extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlowTypeEnum flow;

    @Column(nullable = false)
    private LocalTime operationDate;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    private ExpenseEntity expense;
}

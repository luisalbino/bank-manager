package com.bankmanager.application.entities.expenses;

import com.bankmanager.application.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ExpenseEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDate expireDate;

    @Column(nullable = false)
    private Boolean isPaid;
}

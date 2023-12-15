package com.bankmanager.application.entities.expenses;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
public class ExpenseEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    @ManyToOne(optional = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer expireDay;

    @Column(nullable = false)
    private Boolean paid;

    public boolean isPaid() {
        return Objects.nonNull(paid) && paid;
    }
}

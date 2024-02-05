package com.bankmanager.application.entities.expenses;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.cash.CashFlowEntity;
import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
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

    private LocalDateTime lastTimePaid;

    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER)
    private Collection<CashFlowEntity> cashFlows;

    public boolean isPaid() {
        var maxDate = cashFlows.stream()
                .map(CashFlowEntity::getCompetencyData)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        var result = false;
        if (Objects.nonNull(maxDate)) {
            var lastMonthPaid = LocalDateTimeHelper.getMonthStr(maxDate);
            var thisMonth = LocalDateTimeHelper.getMonthStr(LocalDateTime.now());
            result = lastMonthPaid.equals(thisMonth);
        }

        return result;
    }
}

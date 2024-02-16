package com.bankmanager.application.entities.despesas;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.helpers.LocalDateTimeHelper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "expense_entity")
public class DesepesaEntity extends AbstractEntity {

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
    private Collection<FluxoCaixaEntity> cashFlows;

    public boolean isPaid() {
        var maxDate = cashFlows.stream()
                .map(FluxoCaixaEntity::getCompetencyData)
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

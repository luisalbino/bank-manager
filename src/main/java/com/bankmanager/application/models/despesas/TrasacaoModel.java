package com.bankmanager.application.models.despesas;

import com.bankmanager.application.enums.despesas.transacoes.TipoPerformanceEnum;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrasacaoModel {

    private Long id;

    private Double valor;

    private String valorStr;
    private String performance;

    private String dataCompetencia;
    private String dataPagamentoStr;

    private LocalDateTime dataPagamento;

    @Builder.Default
    private TipoPerformanceEnum tipoPerformance = TipoPerformanceEnum.NEUTRO;

    public Icon getIconePerformance() {
        var icone = VaadinIcon.MINUS.create();
        icone.setColor("#8e918f");

        if (TipoPerformanceEnum.isPositivo(this.getTipoPerformance())) {
            icone = VaadinIcon.ANGLE_DOWN.create();
            icone.setColor("#90EE90");
        }

        if (TipoPerformanceEnum.isNegativo(this.getTipoPerformance())) {
            icone = VaadinIcon.ANGLE_UP.create();
            icone.setColor("#FF474C");
        }

        icone.setSize("15px");
        return icone;
    }

    public String getTooltipPerformance() {
        String resultado = null;

        if (TipoPerformanceEnum.isPositivo(this.getTipoPerformance())) {
            resultado = "Pagamento foi " + getPerformance() + " menor em comparação ao último!";
        }

        if (TipoPerformanceEnum.isNegativo(this.getTipoPerformance())) {
            resultado = "Pagamento foi " + getPerformance() + " maior em comparação ao último!";
        }

        return resultado;
    }
}

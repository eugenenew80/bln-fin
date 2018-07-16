package bln.fin.entity;

import bln.fin.entity.enums.ForecastTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_sale_plan_headers")
public class SalePlanHeader {
    @Id
    @SequenceGenerator(name="fin_sale_plan_headers_s", sequenceName = "fin_sale_plan_headers_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_sale_plan_headers_s")
    private Long id;

    @Column(name="forecast_type_code")
    @Enumerated(EnumType.STRING)
    private ForecastTypeEnum forecastType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "version")
    private Long version;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "transferred_to_erp_date")
    private LocalDateTime transferredToErpDate;

    @OneToMany(mappedBy = "header", fetch = FetchType.LAZY)
    private List<SalePlanLine> lines;
}

package bln.fin.entity;

import bln.fin.entity.enums.PeriodStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_periods")
public class Period {
    @Id
    @SequenceGenerator(name="fin_periods_s", sequenceName = "fin_periods_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_periods_s")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private PeriodStatusEnum status;

    @Column(name = "closed_period_date")
    private LocalDateTime closePeriodDate;
}

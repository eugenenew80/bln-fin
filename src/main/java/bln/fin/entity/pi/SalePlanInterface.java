package bln.fin.entity.pi;

import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_sale_plan_interface")
public class SalePlanInterface implements Monitored {
    @Id
    private Long id;

    @Column(name = "customer_num")
    private String customerNum;

    @Column(name = "item_num")
    private String itemNum;

    @Column(name = "version")
    private Long version;

    @Column(name = "forecast_type")
    private String forecastType;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "amount")
    private double amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "channel")
    private String channel;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private SoapSession session;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

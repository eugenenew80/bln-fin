package bln.fin.entity.pi;

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
@Table(name = "fin_req_line_interface")

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "ReqLineInterface.updateReqLine",
        procedureName = "sap_interface.req_line_transfer"
    )
})
public class ReqLineInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_req_line_interface_s", sequenceName = "fin_req_line_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_req_line_interface_s")
    private Long id;

    @Column(name = "req_num")
    private Long reqNum;

    @Column(name = "pos_num")
    private Long posNum;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "item_num")
    private String itemNum;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "currencyCode")
    private String currencyCode;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "expected_date")
    private LocalDate expectedDate;

    @Column(name = "is_deleted")
    private String deleted;

    @Column(name = "is_unlocked")
    private String unlocked;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private Session session;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

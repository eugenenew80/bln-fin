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
@Table(name = "fin_invoice_status_interface")

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "InvoiceStatusInterface.transfer",
        procedureName = "sap_interface.invoice_status_transfer"
    )
})
public class InvoiceStatusInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_invoice_status_interface_s", sequenceName = "fin_invoice_status_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_invoice_status_interface_s")
    private Long id;

    @Column(name = "bp_type")
    private String bpType;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "ref_doc_num")
    private String refDocNum;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "esf_doc_num")
    private String esfDocNum;

    @Column(name = "esf_doc_date")
    private LocalDate esfDocDate;

    @Column(name = "esf_status")
    private String esfStatus;

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

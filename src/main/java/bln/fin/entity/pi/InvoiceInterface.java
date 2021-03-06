package bln.fin.entity.pi;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_invoice_interface")

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "InvoiceInterface.updateStatuses",
            procedureName = "sap_interface.invoice_sd_update"
    ),

    @NamedStoredProcedureQuery(
        name = "InvoiceInterface.transfer",
        procedureName = "sap_interface.invoice_transfer"
    )
})
public class InvoiceInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_invoice_interface_s", sequenceName = "fin_invoice_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_invoice_interface_s")
    private Long id;

    @Column(name = "bp_type")
    private String bpType;

    @Column(name = "bp_num")
    private String bpNum;

    @Column(name = "contract_num")
    private String contractNum;

    @Column(name = "ext_contract_num")
    private String extContractNum;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "ext_doc_num")
    private String extDocNum;

    @Column(name = "src_doc_num")
    private String srcDocNum;

    @Column(name = "order_num")
    private String orderNum;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "turnover_date")
    private LocalDate turnoverDate;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

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

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<InvoiceLineInterface> lines;
}

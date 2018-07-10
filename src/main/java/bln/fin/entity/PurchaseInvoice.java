package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_purchase_invoices")
public class PurchaseInvoice {
    @Id
    @SequenceGenerator(name="fin_purchase_invoices_s", sequenceName = "fin_purchase_invoices_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_purchase_invoices_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private BusinessPartner vendor;

    @ManyToOne
    @JoinColumn(name = "consignor_id")
    private BusinessPartner consignor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private BusinessPartner customer;

    @ManyToOne
    @JoinColumn(name = "consignee_id")
    private BusinessPartner consignee;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "turnover_date")
    private LocalDate turnoverDate;

    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "esf_doc_num")
    private String esfDocNum;

    @Column(name = "esf_doc_date")
    private LocalDate esfDocDate;

    @Column(name = "esf_status")
    private String esfStatus;

    @Column(name = "erp_doc_num")
    private String erpDocNum;

    @Column(name = "erp_doc_date")
    private LocalDate erpDocDate;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "exchange_date")
    private LocalDate exchangeDate;

    @ManyToOne
    @JoinColumn(name = "src_invoice_id")
    private PurchaseInvoice srcInvoice;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseInvoiceLine> lines;
}

package bln.fin.entity;

import bln.fin.entity.enums.DocTypeEnum;
import bln.fin.entity.enums.InvoiceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_sale_invoices")
public class SaleInvoice {
    @Id
    @SequenceGenerator(name="fin_sale_invoices_s", sequenceName = "fin_sale_invoices_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_sale_invoices_s")
    private Long id;

    @Column(name="doc_type_code")
    @Enumerated(EnumType.STRING)
    private DocTypeEnum docTypeCode;

    @Column(name="invoice_type_code")
    @Enumerated(EnumType.STRING)
    private InvoiceTypeEnum invoiceTypeCode;

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
    private ContractKeg contract;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "turnover_date")
    private LocalDate turnoverDate;

    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "status")
    private String status;

    @Column(name = "due_date")
    private LocalDate dueDate;

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
    private SaleInvoice srcSaleInvoice;

    @Column(name = "transfer_to_erp_date")
    private LocalDateTime transferredToErpDate;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleInvoiceLine> lines;
}

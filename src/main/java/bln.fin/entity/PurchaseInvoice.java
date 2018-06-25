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

    @Column(name = "num")
    private String num;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "turnover_date")
    private LocalDate turnoverDate;

    @Column(name = "esf_num")
    private String esfNum;

    @Column(name = "esf_date")
    private LocalDate esfDate;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "exchange_date")
    private LocalDate exchangeDate;

    @ManyToOne
    @JoinColumn(name = "src_purchase_invoice_id")
    private PurchaseInvoice srcPurchaseInvoice;

    @OneToMany(mappedBy = "purchaseInvoice", fetch = FetchType.LAZY)
    private List<SaleInvoiceLine> lines;
}

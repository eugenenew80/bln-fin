package bln.fin.entity;

import bln.fin.entity.enums.InvoiceLineTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_purchase_invoice_lines")
public class PurchaseInvoiceLine {
    @Id
    @SequenceGenerator(name="fin_purchase_invoice_lines_s", sequenceName = "fin_purchase_invoice_lines_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_purchase_invoice_lines_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_invoice_id")
    private PurchaseInvoiceLine purchaseInvoice;

    @Column(name="line_type_code")
    @Enumerated(EnumType.STRING)
    private InvoiceLineTypeEnum lineTypeCode;

    @ManyToOne
    @JoinColumn(name = "gjs_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "tax_rate")
    private Double taxRateValue;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
    private TaxRate taxRate;
}

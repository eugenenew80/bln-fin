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
    @JoinColumn(name = "invoice_id")
    private PurchaseInvoice invoice;

    @Column(name="line_type_code")
    @Enumerated(EnumType.STRING)
    private InvoiceLineTypeEnum lineTypeCode;

    @Column(name="pos_num")
    private Long posNum;

    @ManyToOne
    @JoinColumn(name = "gjs_id")
    private Item item;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
    private TaxRate taxRate;
}

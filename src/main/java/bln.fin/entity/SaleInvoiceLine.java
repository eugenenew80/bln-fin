package bln.fin.entity;

import bln.fin.entity.enums.InvoiceLineTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_sale_invoice_lines")
public class SaleInvoiceLine {
    @Id
    @SequenceGenerator(name="fin_sale_invoice_lines_s", sequenceName = "fin_sale_invoice_lines_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_sale_invoice_lines_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private SaleInvoice invoice;

    @Column(name="line_type_code")
    @Enumerated(EnumType.STRING)
    private InvoiceLineTypeEnum lineTypeCode;

    @Column(name="line_num")
    private Long lineNum;

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

    @Column(name = "discount")
    private Double discount;

    @Column(name = "tax_rate")
    private Double taxRateValue;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
    private TaxRate taxRate;
}

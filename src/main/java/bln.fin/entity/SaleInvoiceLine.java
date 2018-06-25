package bln.fin.entity;

import bln.fin.entity.enums.DocTypeEnum;
import bln.fin.entity.enums.InvoiceLineTypeEnum;
import bln.fin.entity.enums.InvoiceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

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
    @JoinColumn(name = "sale_invoice_id")
    private SaleInvoiceLine saleInvoice;

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

    @Column(name = "discount")
    private Double discount;

    @Column(name = "base_unit_price")
    private Double baseUnitPrice;

    @Column(name = "base_amount")
    private Double baseAmount;

    @Column(name = "base_discount")
    private Double baseDiscount;

    @Column(name = "tax_rate")
    private Double taxRate;
}

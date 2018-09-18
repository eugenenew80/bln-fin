package bln.fin.entity.pi;

import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_invoice_line_interface")
public class InvoiceLineInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_invoice_line_interface_s", sequenceName = "fin_invoice_line_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_invoice_line_interface_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_interface_id")
    private InvoiceInterface invoice;

    @Column(name = "bp_num")
    private String bpNum;

    @Column(name = "item_num")
    private Long itemNum;

    @Column(name = "pos_num")
    private Long posNum;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "tax_rate")
    private Double taxRate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

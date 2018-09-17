package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_invoice_line_interface")
public class InvoiceLineInterface {
    @Id
    @SequenceGenerator(name="fin_invoice_line_interface_s", sequenceName = "fin_invoice_line_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_invoice_line_interface_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_interface_id")
    private InvoiceInterface invoice;

    @Column(name = "pos_num")
    private Long posNum;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

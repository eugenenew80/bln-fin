package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_sale_plan_lines")
public class SalePlanLine {
    @Id
    @SequenceGenerator(name="fin_sale_plan_lines_s", sequenceName = "fin_sale_plan_lines_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_sale_plan_lines_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private SalePlanHeader header;

    @ManyToOne
    @JoinColumn(name = "gjs_id")
    private Item item;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "amount")
    private Double amount;
}

package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_req_items")
public class ReqItem {
    @Id
    @SequenceGenerator(name="fin_req_items_s", sequenceName = "fin_req_items_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_req_items_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private ReqLine line;

    @Column(name = "req_num")
    private Long lineNum;

    @Column(name = "item_num")
    private String itemNum;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Double price;
}

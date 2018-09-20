package bln.fin.entity.pi;

import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_contract_line_interface")
public class ContractLineInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_contract_line_interface_s", sequenceName = "fin_contract_line_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_contract_line_interface_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contract_interface_id")
    private ContractInterface contract;

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

    @Column(name = "reqNum")
    private String reqNum;

    @Column(name = "reqPosNum")
    private String reqPosNum;

    @Column(name = "mat_group")
    private String matGroup;

    @Column(name = "cost_center")
    private String costCenter;

    @Column(name = "account")
    private String account;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

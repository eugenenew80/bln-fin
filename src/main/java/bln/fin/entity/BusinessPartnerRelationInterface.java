package bln.fin.entity;

import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_relation_interface")
public class BusinessPartnerRelationInterface implements Monitored {
    @Id
    @SequenceGenerator(name="dict_bp_relation_interface_s", sequenceName = "dict_bp_relation_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_relation_interface_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bp_interface_id")
    private BusinessPartnerInterface businessPartner;

    @Column(name = "relation_type")
    private String relationType;

    @Column(name = "bp_num")
    private String bpNum;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

package bln.fin.entity.pi;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_relation_interface")

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "BpRelationInterface.updateStatuses",
        procedureName = "sap_interface.bp_relation_transfer"
    )
})
public class BpRelationInterface implements Monitored {
    @Id
    @SequenceGenerator(name="dict_bp_relation_interface_s", sequenceName = "dict_bp_relation_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_relation_interface_s")
    private Long id;

    @Column(name = "relation_type")
    private String relationType;

    @Column(name = "bp_num")
    private String bpNum;

    @Column(name = "bp_num_rel")
    private String bpNumRel;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private Session session;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

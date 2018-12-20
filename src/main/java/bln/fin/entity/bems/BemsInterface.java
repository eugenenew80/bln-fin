package bln.fin.entity.bems;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "bems_interface")
public class BemsInterface {
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "metering_date")
    private LocalDateTime meteringDate;

    @Column(name = "direction")
    private Byte direction;

    @Column(name = "val")
    private Long val;

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

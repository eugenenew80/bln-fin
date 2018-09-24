package bln.fin.entity.pi;

import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "ws_session_messages")
@DynamicUpdate
public class SessionMessage {

    @Id
    @SequenceGenerator(name="ws_session_messages_s", sequenceName = "ws_session_messages_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ws_session_messages_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private Session session;

    @Column(name="object_code")
    private String objectCode;

    @Column(name="object_id")
    private String objectId;

    @Column(name="sap_id")
    private String sapId;

    @Column(name="system_code")
    private String system;

    @Column(name="msg_num")
    private String msgNum;

    @Column(name="msg_type")
    private String msgType;

    @Column(name="msg")
    private String msg;
}

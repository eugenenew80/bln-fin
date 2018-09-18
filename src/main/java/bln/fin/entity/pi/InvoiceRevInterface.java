package bln.fin.entity.pi;

import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_invoice_rev_interface")
public class InvoiceRevInterface implements Monitored {
    @Id
    private Long id;

    @Column(name = "doc_num")
    protected String docNum;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "rev_date")
    private LocalDate revDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private SoapSession session;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

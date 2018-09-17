package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_interface_tl")
public class BusinessPartnerTranslateInterface {
    @Id
    @SequenceGenerator(name="dict_bp_interface_tl_s", sequenceName = "dict_bp_interface_tl_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_interface_tl_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bp_interface_id")
    private BusinessPartnerInterface businessPartner;

    @Column(name="lang")
    private String lang;

    @Column(name = "name")
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

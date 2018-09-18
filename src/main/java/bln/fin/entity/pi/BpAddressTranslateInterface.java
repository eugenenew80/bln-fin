package bln.fin.entity.pi;

import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_address_interface_tl")
public class BpAddressTranslateInterface implements Monitored {
    @Id
    @SequenceGenerator(name="dict_bp_address_interface_tl_s", sequenceName = "dict_bp_address_interface_tl_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_address_interface_tl_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bp_address_interface_id")
    private BpAddressInterface address;

    @Column(name="lang")
    private String lang;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

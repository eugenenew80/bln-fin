package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_address_interface")
public class BusinessPartnerAddressInterface {
    @Id
    @SequenceGenerator(name="dict_bp_address_interface_s", sequenceName = "dict_bp_address_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_address_interface_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bp_interface_id")
    private BusinessPartnerInterface businessPartner;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "house_num")
    private String houseNum;

    @Column(name = "build_num")
    private String buildNum;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "room")
    private String room;

    @Column(name = "area")
    private String area;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "int_phone_num")
    private String intPhoneNum;

    @Column(name = "cell_phone_num")
    private String cellPhoneNum;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BusinessPartnerAddressTranslateInterface> translates;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

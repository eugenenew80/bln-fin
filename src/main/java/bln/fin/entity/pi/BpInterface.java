package bln.fin.entity.pi;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_interface")
public class BpInterface implements Monitored {
    @Id
    @SequenceGenerator(name="dict_bp_interface_s", sequenceName = "dict_bp_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_interface_s")
    private Long id;

    @Column(name="bp_num")
    private String bpNum;

    @Column(name="search_group")
    private String group;

    @Column(name="search_criteria")
    private String searchCriteria;

    @Column(name="found_date")
    private LocalDate foundDate;

    @Column(name="liq_date")
    private LocalDate liqDate;

    @Column(name="industry")
    private String industry;

    @Column(name="tax_number_type")
    private String taxNumberType;

    @Column(name="tax_number")
    private String taxNumber;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ws_session_id")
    private Session session;

    @OneToMany(mappedBy = "businessPartner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BpAddressInterface> addresses;

    @OneToMany(mappedBy = "businessPartner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BpBankAccountInterface> bankAccounts;

    @OneToMany(mappedBy = "businessPartner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BpTranslateInterface> translates;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

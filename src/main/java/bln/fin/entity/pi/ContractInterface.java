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
@Table(name = "fin_contract_interface")
public class ContractInterface implements Monitored {
    @Id
    @SequenceGenerator(name="fin_contract_interface_s", sequenceName = "fin_contract_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_contract_interface_s")
    private Long id;

    @Column(name = "bp_type")
    private String bpType;

    @Column(name = "bp_num")
    private String vendorNum;

    @Column(name = "cons_bp_num")
    private String consignorNum;

    @Column(name = "contract_num")
    private String contractNum;

    @Column(name = "ext_contract_num")
    private String extContractNum;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "company_account_num")
    private String companyAccountNum;

    @Column(name = "bp_account_num")
    private String bpAccountNum;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "channel")
    private String channel;

    @Column(name = "sale_depart_code")
    private String saleDepartCode;

    @Column(name = "purchase_group")
    private String purchaseGroup;

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

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ContractLineInterface> lines;
}

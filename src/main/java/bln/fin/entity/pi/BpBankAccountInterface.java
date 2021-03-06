package bln.fin.entity.pi;

import bln.fin.entity.interfaces.Monitored;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_bp_bank_account_interface")
public class BpBankAccountInterface implements Monitored {
    @Id
    @SequenceGenerator(name="dict_bp_bank_account_interface_s", sequenceName = "dict_bp_bank_account_interface_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_bp_bank_account_interface_s")
    private Long id;

    @Column(name="bankkey")
    private String bankkey;

    @ManyToOne
    @JoinColumn(name = "bp_interface_id")
    private BpInterface businessPartner;

    @Column(name = "country")
    private String country;

    @Column(name = "bik")
    private String bik;

    @Column(name = "account")
    private String account;

    @Column(name = "iban")
    private String iban;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}

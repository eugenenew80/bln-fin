package bln.fin.entity;

import bln.fin.entity.enums.DebtTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_receipt_applications")
public class ReceiptApplication {

    @Id
    @SequenceGenerator(name="fin_receipt_applications_s", sequenceName = "fin_receipt_applications_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_receipt_applications_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private SaleInvoice invoice;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private BusinessPartner payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private BusinessPartner payee;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name="debt_type_code")
    @Enumerated(EnumType.STRING)
    private DebtTypeEnum debtTypeCode;

    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;
}

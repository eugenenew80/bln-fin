package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "cm_doc_rfc")
public class ContractRfc {

    @Id
    private Long id;

    @Column(name = "doc_number")
    private String contractNum;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @ManyToOne
    @JoinColumn(name = "bp1_id")
    private BusinessPartner bp1;

    @ManyToOne
    @JoinColumn(name = "bp2_id")
    private BusinessPartner bp2;

    @Column(name = "transferred_to_erp_date")
    private LocalDateTime transferredToErpDate;
}

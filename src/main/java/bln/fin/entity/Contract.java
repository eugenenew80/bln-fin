package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "cm_doc_keg_cte")
public class Contract {

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
}

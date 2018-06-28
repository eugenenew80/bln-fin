package bln.fin.entity;

import bln.fin.jpa.BooleanToIntConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_req_lines")
public class ReqLine {
    @Id
    @SequenceGenerator(name="fin_req_lines_s", sequenceName = "fin_req_lines_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_req_lines_s")
    private Long id;

    @Column(name = "req_num")
    private Long reqNum;

    @Column(name = "pos_num")
    private Long posNum;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currencyCode")
    private String currencyCode;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "expected_date")
    private Date expectedDate;

    @Column(name = "is_deleted")
    @Convert(converter = BooleanToIntConverter.class)
    private Boolean deleted;

    @Column(name = "is_unlocked")
    @Convert(converter = BooleanToIntConverter.class)
    private Boolean unlocked;

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReqItem> lines;
}

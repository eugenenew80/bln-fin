package bln.fin.entity;

import bln.fin.jpa.BooleanToIntConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_total_delivery_headers")
public class TotalDeliveryHeader {

    @Id
    @SequenceGenerator(name="fin_total_delivery_headers_s", sequenceName = "fin_total_delivery_headers_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_total_delivery_headers_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private Period period;

    @Column(name = "version")
    private Long version;

    @Convert(converter = BooleanToIntConverter.class)
    private Boolean isFinal;

    @OneToMany(mappedBy = "header", fetch = FetchType.LAZY)
    private List<TotalDeliveryLine> lines;
}

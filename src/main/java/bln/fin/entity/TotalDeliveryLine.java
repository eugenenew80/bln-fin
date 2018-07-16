package bln.fin.entity;

import bln.fin.jpa.BooleanToIntConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "fin_total_delivery_lines")
public class TotalDeliveryLine {
    @Id
    @SequenceGenerator(name="fin_total_delivery_lines_s", sequenceName = "fin_total_delivery_lines_s", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_total_delivery_lines_s")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private TotalDeliveryHeader header;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private BusinessPartner vendor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private BusinessPartner customer;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractKeg contract;

    @ManyToOne
    @JoinColumn(name = "gjs_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "is_invoice_created")
    @Convert(converter = BooleanToIntConverter.class)
    private Boolean isInvoiceCreated;

    @Column(name = "is_estimated_created")
    @Convert(converter = BooleanToIntConverter.class)
    private Boolean isEstimatedCreated;
}

package bln.fin.soap.invoice;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item", namespace = "http://bis.kegoc.kz/soap")
public class InvoiceLineDto {
    @XmlElement(required = true)
    private Long posNum;

    @XmlElement(required = true)
    private String posName;

    @XmlElement(required = true)
    private String itemNum;

    @XmlElement(required = true)
    private String unit;

    @XmlElement(required = true)
    private Double quantity;

    @XmlElement(required = true)
    private Double price;

    @XmlElement(required = true)
    private Double amount;

    @XmlElement(required = true)
    private Double taxRate;
}

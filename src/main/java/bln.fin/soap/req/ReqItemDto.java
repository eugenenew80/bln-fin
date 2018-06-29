package bln.fin.soap.req;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqItem", namespace = "http://bis.kegoc.kz/soap")
public class ReqItemDto {
    @XmlElement(required = true)
    private Long rowNum;

    @XmlElement(required = true)
    private String itemNum;

    @XmlElement(required = true)
    private String itemName;

    @XmlElement(required = true)
    private Double quantity;

    @XmlElement(required = true)
    private String unit;

    @XmlElement(required = true)
    private Double price;
}

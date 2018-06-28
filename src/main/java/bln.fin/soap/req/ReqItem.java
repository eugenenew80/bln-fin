package bln.fin.soap.req;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqItem {
    @XmlElement(required = true)
    private Long lineNum;

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

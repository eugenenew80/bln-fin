package bln.fin.soap.req;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqLine {
    @XmlElement(required = true)
    private Long reqNum;

    @XmlElement(required = true)
    private Long posNum;

    @XmlElement(required = true)
    private String posName;

    @XmlElement(required = true)
    private Double quantity;

    @XmlElement(required = true)
    private String unit;

    @XmlElement(required = true)
    private Double amount;

    @XmlElement(required = true)
    private String currencyCode;

    @XmlElement(required = true)
    private String companyCode;

    @XmlElement(required = true)
    private Date expectedDate;

    @XmlElement(defaultValue = "N")
    private String deleted;

    @XmlElement(defaultValue = "N")
    private String unlocked;

    @XmlElement(required = true, name = "item")
    private List<ReqItem> items;
}
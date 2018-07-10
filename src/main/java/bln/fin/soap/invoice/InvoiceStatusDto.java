package bln.fin.soap.invoice;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceStatus", namespace = "http://bis.kegoc.kz/soap")
public class InvoiceStatusDto {
    @XmlElement(required = true)
    private String bpType;

    @XmlElement(required = true)
    private String docNum;

    @XmlElement(required = true)
    private Date docDate;

    @XmlElement(required = true)
    private String esfDocNum;

    @XmlElement(required = true)
    private Date esfDocDate;

    @XmlElement(required = true)
    private String esfStatus;
}

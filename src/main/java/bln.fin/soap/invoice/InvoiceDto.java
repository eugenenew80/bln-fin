package bln.fin.soap.invoice;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Invoice", namespace = "http://bis.kegoc.kz/soap")
public class InvoiceDto {
    @XmlElement(required = true)
    private String bpType;

    @XmlElement(required = true)
    private String docNum;

    @XmlElement(required = true)
    private Date docDate;

    @XmlElement(required = true)
    private String extDocNum;

    @XmlElement(required = true)
    private Date accountingDate;

    @XmlElement(required = true)
    private String bpNum;

    @XmlElement(required = true)
    private String contractNum;

    @XmlElement(required = true)
    private String extContractNum;

    @XmlElement(required = true)
    private String companyCode;

    @XmlElement(required = true)
    private String sysCode;

    @XmlElement(required = true)
    private Double amount;

    @XmlElement(required = true)
    private Double tax;

    @XmlElement(required = true, name = "item")
    private List<InvoiceLineDto> items;
}

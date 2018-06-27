package bln.fin.soap.debt;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Debt {
    @XmlElement(required = true)
    private String businessPartnerType;

    @XmlElement(required = true)
    private String debtType;

    @XmlElement(required = true)
    private String accountingDate;

    @XmlElement(required = true)
    private Double amount;

    @XmlElement(required = true)
    private String currencyCode;

    @XmlElement(defaultValue = "0")
    private Double exchangeRate;

    @XmlElement(required = true)
    private String docNum;

    @XmlElement(required = true)
    private Date docDate;

    @XmlElement(required = true)
    private String businessPartnerNum;

    @XmlElement(required = true)
    private String contractNum;

    @XmlElement(required = true)
    private String externalContractNum;

    @XmlElement(required = true)
    private String companyCode;
}

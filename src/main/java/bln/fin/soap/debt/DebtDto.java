package bln.fin.soap.debt;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Debt", namespace = "http://bis.kegoc.kz/soap")
public class DebtDto {
    @XmlElement(required = true)
    private String bpType;

    @XmlElement(required = true)
    private String debtType;

    @XmlElement(required = true)
    private Date accountingDate;

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
    private String bpNum;

    @XmlElement(required = true)
    private String contractNum;

    @XmlElement(required = true)
    private String extContractNum;

    @XmlElement(required = true)
    private String companyCode;
}

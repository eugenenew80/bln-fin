package bln.fin.soap.debt;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Debt {
    private String businessPartnerType;
    private String debtType;
    private String accountingDate;
    private Double amount;
    private String currencyCode;
    private Double exchangeRate;
    private String docNum;
    private Date docDate;
    private String businessPartnerNum;
    private String contractNum;
    private String externalContractNum;
    private String companyCode;
}

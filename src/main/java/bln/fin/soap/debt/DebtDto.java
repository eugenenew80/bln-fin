package bln.fin.soap.debt;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Debt", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Дебиторская и кредиторская задолженность")
public class DebtDto {

    @XmlElement(required = true)
    @Documentation("Тип делового партнера: K - поставщик, D - заказчик")
    private String bpType;

    @XmlElement(required = true)
    @Documentation("Тип задолженности: I - СФ, D - аванс")
    private String debtType;

    @XmlElement(required = true)
    @Documentation("Дата задолженности")
    private Date accountingDate;

    @XmlElement(required = true)
    @Documentation("Сумма задолженности")
    private Double amount;

    @XmlElement(required = true)
    @Documentation("Валюта")
    private String currencyCode;

    @XmlElement
    @Documentation("Курс валюты")
    private Double exchangeRate;

    @XmlElement(required = true)
    @Documentation("№ документа в SAP")
    private String docNum;

    @XmlElement(required = true)
    @Documentation("Дата документа в SAP")
    private Date docDate;

    @XmlElement(required = true)
    @Documentation("№ делового партнера в SAP")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("№ договора в SAP")
    private String contractNum;

    @XmlElement(required = true)
    @Documentation("Внешний № договора")
    private String extContractNum;

    @XmlElement(required = true)
    @Documentation("Балансовая единица")
    private String companyCode;
}

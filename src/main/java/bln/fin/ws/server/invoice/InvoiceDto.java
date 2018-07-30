package bln.fin.ws.server.invoice;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Invoice", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Счета-фактуры")
public class InvoiceDto {

    @XmlElement(required = true)
    @Documentation("Тип делового партнера: K - поставщик, D - заказчик")
    private String bpType;

    @XmlElement(required = true)
    @Documentation("№ документа в SAP")
    private String docNum;

    @XmlElement(required = true)
    @Documentation("Дата документа в SAP")
    private Date docDate;

    @XmlElement(required = true)
    @Documentation("Внешний № документа")
    private String extDocNum;

    @XmlElement(required = true)
    @Documentation("Дата проводки")
    private Date accountingDate;

    @XmlElement(required = true)
    @Documentation("№ делового партнера-поставщика в SAP")
    private String vendorNum;

    @XmlElement(required = true)
    @Documentation("№ договора в SAP")
    private String contractNum;

    @XmlElement(required = true)
    @Documentation("Внешний № договора")
    private String extContractNum;

    @XmlElement(required = true)
    @Documentation("Балансовая единица")
    private String companyCode;

    @XmlElement(required = true)
    @Documentation("Сумма СФ включая НДС")
    private Double amount;

    @XmlElement(required = true)
    @Documentation("Сумма НДС")
    private Double tax;

    @XmlElement(required = true)
    @Documentation("Код валюты")
    private String currencyCode;

    @XmlElement
    @Documentation("Курс валюты")
    private Double exchangeRate;

    @XmlElement(required = true, name = "line")
    @Documentation("Список позиций СФ")
    private List<InvoiceLineDto> lines;
}

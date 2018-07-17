package bln.fin.ws.server.saleInvoice;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleInvoice", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Счета-фактуры")
public class SaleInvoiceDto {

    @XmlElement(required = true)
    @Documentation("Иж документа в БИС")
    private Long id;

    @XmlElement
    @Documentation("№ документа в SAP")
    private String sapDocNum;

    @XmlElement
    @Documentation("Дата документа в SAP")
    private Date sapDocDate;

    @XmlElement(required = true)
    @Documentation("Внешний № документа")
    private String docNum;

    @XmlElement(required = true)
    @Documentation("Дата проводки")
    private Date accountingDate;

    @XmlElement(required = true)
    @Documentation("№ делового партнера в SAP")
    private String bpNum;

    @XmlElement
    @Documentation("№ договора в SAP")
    private String contractNum;

    @XmlElement(required = true)
    @Documentation("Внешний № договора")
    private String extContractNum;

    @XmlElement(required = true)
    @Documentation("Балансовая единица")
    private String companyCode;

    @XmlElement(required = true)
    @Documentation("Идентификатор системы-отправителя")
    private String system;

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
    private List<SaleInvoiceLineDto> lines;
}

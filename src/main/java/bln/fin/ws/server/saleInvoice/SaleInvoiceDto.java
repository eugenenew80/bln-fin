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
    @Documentation("Ид документа в БИС")
    private Long id;

    @XmlElement(required = true)
    @Documentation("Вид документа")
    private String docType;

    @XmlElement
    @Documentation("№ документа SAP")
    private String sapDocNum;

    @XmlElement
    @Documentation("Дата совершения оборота")
    private Date turnoverDate;

    @XmlElement(required = true)
    @Documentation("№ документа")
    private String docNum;

    @XmlElement(required = true)
    @Documentation("Дата проводки")
    private Date accountingDate;

    @XmlElement(required = true)
    @Documentation("№ делового партнера-заказчика")
    private String customerNum;

    @XmlElement(required = true)
    @Documentation("№ делового партнера-грузополучателя")
    private String consigneeNum;

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
    @Documentation("Сумма СФ включая НДС")
    private Double amount;

    @XmlElement(required = true)
    @Documentation("Сумма НДС")
    private Double tax;

    @XmlElement
    @Documentation("Код валюты")
    private String currencyCode;

    @XmlElement(required = true, name = "line")
    @Documentation("Список позиций СФ")
    private List<SaleInvoiceLineDto> lines;
}

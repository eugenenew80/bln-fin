package bln.fin.ws.server.saleInvoice;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Line", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Позиция СФ")
public class SaleInvoiceLineDto {

    @XmlElement
    @Documentation("№ материала/услуги SAP")
    private String itemNum;

    @XmlElement(required = true)
    @Documentation("Наименование позиции")
    private String itemName;

    @XmlElement(required = true)
    @Documentation("Единица измерения")
    private String unit;

    @XmlElement(required = true)
    @Documentation("Количество")
    private Double quantity;

    @XmlElement(required = true)
    @Documentation("Цена за единицу")
    private Double price;

    @XmlElement(required = true)
    @Documentation("Стоимость без НДС")
    private Double amount;
}

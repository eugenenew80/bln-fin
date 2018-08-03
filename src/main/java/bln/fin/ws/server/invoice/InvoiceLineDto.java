package bln.fin.ws.server.invoice;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Line", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Позиция СФ")
public class InvoiceLineDto {

    @XmlElement(required = true)
    @Documentation("№ позиции в СФ")
    private Long posNum;

    @XmlElement(required = true)
    @Documentation("Наименование позиции")
    private String posName;

    @XmlElement(required = true)
    @Documentation("Единица измерения")
    private String unit;

    @XmlElement(required = true)
    @Documentation("Количество")
    private Double quantity;

    @XmlElement(required = true)
    @Documentation("Стоимость без НДС")
    private Double amount;
}

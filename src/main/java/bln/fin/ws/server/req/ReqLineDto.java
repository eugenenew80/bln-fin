package bln.fin.ws.server.req;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqLine", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Строка заявки")
public class ReqLineDto {

    @XmlElement(required = true)
    @Documentation("Номер заявки")
    private Long reqNum;

    @XmlElement(required = true)
    @Documentation("Номер позиции")
    private Long posNum;

    @XmlElement
    @Documentation("Наименование позиции")
    private String posName;

    @XmlElement
    @Documentation("Номер материала")
    private String itemNum;

    @XmlElement
    @Documentation("Количество")
    private Double quantity;

    @XmlElement
    @Documentation("Цена за единицу")
    private Double price;

    @XmlElement
    @Documentation("Единица измерения")
    private String unit;

    @XmlElement
    @Documentation("Валюта")
    @Facets(minLength = 3, maxLength = 3)
    private String currencyCode;

    @XmlElement
    @Documentation("Балансовая единица")
    @Facets(minLength = 4, maxLength = 4)
    private String companyCode;

    @XmlElement
    @Documentation("Ожидаемая дата поставки")
    private Date expectedDate;

    @XmlElement(defaultValue = "N", required = true)
    @Documentation("Флаг удаления заявки")
    @Facets(minLength = 1, maxLength = 1)
    private String deleted;

    @XmlElement(defaultValue = "N", required = true)
    @Documentation("Флаг деблокирования заявки")
    @Facets(minLength = 1, maxLength = 1)
    private String unlocked;
}

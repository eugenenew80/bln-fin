package bln.fin.ws.server.req;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

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

    @XmlElement(required = true)
    @Documentation("Наименование позиции")
    private String posName;

    @XmlElement(required = true)
    @Documentation("Количество")
    private Double quantity;

    @XmlElement(required = true)
    @Documentation("Единица измерения")
    private String unit;

    @XmlElement(required = true)
    @Documentation("Стоимость")
    private Double amount;

    @XmlElement(required = true)
    @Documentation("Валюта")
    @Facets(minLength = 3, maxLength = 3)
    private String currencyCode;

    @XmlElement(required = true)
    @Documentation("Балансовая единица")
    @Facets(minLength = 4, maxLength = 4)
    private String companyCode;

    @XmlElement(required = true)
    @Documentation("Ожидаемая дата поставки")
    private Date expectedDate;

    @XmlElement(defaultValue = "N")
    @Documentation("Флаг удаления заявки")
    @Facets(minLength = 1, maxLength = 1)
    private String deleted;

    @XmlElement(defaultValue = "N")
    @Documentation("Флаг деблокирования заявки")
    @Facets(minLength = 1, maxLength = 1)
    private String unlocked;

    @XmlElement(required = true, name = "item")
    @Documentation("Позиции из каталога услуг")
    private List<ReqItemDto> items;
}

package bln.fin.ws.server.req;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqItem", namespace = "http://bis.kegoc.kz/server")
@Documentation("Позиция из каталога услуг")
public class ReqItemDto {

    @XmlElement(required = true)
    @Documentation("Номер строки")
    private Long rowNum;

    @XmlElement(required = true)
    @Documentation("Номер услуги из каталога услуг")
    private String itemNum;

    @XmlElement(required = true)
    @Documentation("Наименование услуги")
    private String itemName;

    @XmlElement(required = true)
    @Documentation("Количество")
    private Double quantity;

    @XmlElement(required = true)
    @Documentation("Единица измерения")
    private String unit;

    @XmlElement(required = true)
    @Documentation("Цена за единицу")
    private Double price;
}

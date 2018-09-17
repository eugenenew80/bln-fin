package bln.fin.ws.server.req;

import bln.fin.entity.ReqLineInterface;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.Date;

import static bln.fin.common.Util.toLocalDate;

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

    public ReqLineInterface toInterface(final ReqLineInterface line) {
        line.setReqNum(getReqNum());
        line.setPosNum(getPosNum());
        line.setPosName(getPosName());
        line.setItemNum(getItemNum());
        line.setQuantity(getQuantity());
        line.setPrice(getPrice());
        line.setUnit(getUnit());
        line.setCurrencyCode(getCurrencyCode());
        line.setCompanyCode(getCompanyCode());
        line.setExpectedDate(toLocalDate(getExpectedDate()));
        line.setDeleted(getDeleted());
        line.setUnlocked(getUnlocked());
        return line;
    }
}

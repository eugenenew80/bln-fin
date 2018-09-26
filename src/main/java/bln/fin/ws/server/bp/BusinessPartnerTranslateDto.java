package bln.fin.ws.server.bp;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartnerTranslate", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Переводы")
public class BusinessPartnerTranslateDto {

    @XmlAttribute(required = true)
    @Documentation("Код языка")
    private String lang;

    @XmlElement(required = true)
    @Documentation("Наименование делового партнера")
    private String name;

    @XmlElement
    @Documentation("Полное наименование делового партнера")
    private String fullName;
}

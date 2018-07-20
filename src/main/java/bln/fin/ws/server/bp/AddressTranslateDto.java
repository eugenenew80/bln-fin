package bln.fin.ws.server.bp;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressTranslate", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Адрес - перводы")
public class AddressTranslateDto {

    @XmlAttribute(required = true)
    @Documentation("Код языка")
    private String lang;

    @XmlElement(required = true)
    @Documentation("Город")
    private String city;

    @XmlElement(required = true)
    @Documentation("Улица")
    private String street;
}

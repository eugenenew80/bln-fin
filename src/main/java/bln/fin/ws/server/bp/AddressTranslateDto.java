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

    @XmlElement
    @Documentation("Город")
    private String city;

    @XmlElement
    @Documentation("Улица")
    private String street;
}

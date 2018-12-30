package bln.fin.ws.server.bp;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressTranslate", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Адрес - перводы")
public class AddressTranslateDto {

    @XmlAttribute(required = true)
    @Documentation("Код языка")
    @NotNull(message = "Поля является обязательным")
    private String lang;

    @XmlElement
    @Documentation("Город")
    @NotNull(message = "Поля является обязательным")
    private String city;

    @XmlElement
    @Documentation("Улица")
    @NotNull(message = "Поля является обязательным")
    private String street;
}

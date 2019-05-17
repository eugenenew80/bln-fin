package bln.fin.ws.server.bp;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Адрес")
public class AddressDto {

    @XmlElement(required = true)
    @Documentation("Тип адреса: ACTUAL - фактический, LEGAL - юридический")
    @NotNull(message = "Поля является обязательным")
    private String addressType;

    @XmlElement
    @Documentation("Код страны")
    @NotNull(message = "Поля является обязательным")
    private String country;

    @XmlElement
    @Documentation("Код региона")
    @NotNull(message = "Поля является обязательным")
    private String region;

    @XmlElement
    @Documentation("№ дома")
    private String houseNum;

    @XmlElement
    @Documentation("№ строения")
    private String buildNum;

    @XmlElement
    @Documentation("Почтовый индекс")
    private String postCode;

    @XmlElement
    @Documentation("№ кабинета")
    private String room;

    @XmlElement
    @Documentation("Район")
    private String area;

    @XmlElement
    @Documentation("№ телефона")
    private String phoneNum;

    @XmlElement
    @Documentation("Внутренний №  телефона")
    private String intPhoneNum;

    @XmlElement
    @Documentation("№ мобильного телефона")
    private String cellPhoneNum;

    @XmlElement
    @Documentation("Факс")
    private String fax;

    @XmlElement
    @Documentation("Адрес электронной почты")
    private String email;

    @XmlElement(name = "translate")
    @Documentation("Перевод")
    private List<AddressTranslateDto> translates;

    @XmlElement
    @Documentation("Глобальный идентификатор адреса")
    @Size(min = 1, max = 100)
    private String guid;
}

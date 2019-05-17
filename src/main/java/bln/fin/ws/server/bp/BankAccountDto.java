package bln.fin.ws.server.bp;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccount", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Банковский счёт")
public class BankAccountDto {

    @XmlElement(required = true)
    @Documentation("Код страны банка")
    @NotNull(message = "Поля является обязательным")
    private String country;

    @XmlElement(required = true)
    @Documentation("БИК банка")
    @NotNull(message = "Поля является обязательным")
    private String bik;

    @XmlElement
    @Documentation("№ банковского счёта")
    private String account;

    @XmlElement
    @Documentation("IBAN")
    private String iban;

    @XmlElement
    @Documentation("Глобальный идентификатор банковского счёта")
    @NotNull
    @Size(min = 1, max = 4)
    private String bankkey;
}

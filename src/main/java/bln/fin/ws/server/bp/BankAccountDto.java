package bln.fin.ws.server.bp;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccount", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Банковский счёт")
public class BankAccountDto {

    @XmlElement(required = true)
    @Documentation("Код страны банка")
    private String country;

    @XmlElement(required = true)
    @Documentation("БИК банка")
    private String bik;

    @XmlElement(required = true)
    @Documentation("№ банковского счёта")
    private String account;

    @XmlElement(required = true)
    @Documentation("IBAN")
    private String iban;
}

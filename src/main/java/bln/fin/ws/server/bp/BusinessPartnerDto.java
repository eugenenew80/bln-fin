package bln.fin.ws.server.bp;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartner", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Деловой партнер")
public class BusinessPartnerDto {

    @XmlElement
    @Documentation("IDOC")
    private String iDoc;

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    @NotNull(message = "Поля является обязательным")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("Группа")
    @NotNull(message = "Поля является обязательным")
    private String group;

    @XmlElement(required = true)
    @Documentation("Критерий поиска")
    @NotNull(message = "Поля является обязательным")
    private String searchCriteria;

    @XmlElement
    @Documentation("Дата основания")
    private Date foundDate;

    @XmlElement
    @Documentation("Дата ликвидации")
    private Date liqDate;

    @XmlElement
    @Documentation("Отрасль")
    private String industry;

    @XmlElement(required = true)
    @Documentation("Типа налогового номера")
    @NotNull(message = "Поля является обязательным")
    private String taxNumberType;

    @XmlElement(required = true)
    @Documentation("Налоговый номер")
    @NotNull(message = "Поля является обязательным")
    private String taxNumber;

    @XmlElement(required = true, name = "translate")
    @Documentation("Перевод")
    private List<BusinessPartnerTranslateDto> translates;

    @XmlElement(name = "address")
    @Documentation("Адреса")
    private List<AddressDto> addresses;

    @XmlElement(name = "bankAccount")
    @Documentation("Список банковских счетов")
    private List<BankAccountDto> bankAccounts;
}

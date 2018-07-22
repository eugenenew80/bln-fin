package bln.fin.ws.server.saleInvoiceRev;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleInvoiceRev", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Сторно счета-фактуры")
public class SaleInvoiceRevDto {

    @XmlElement(required = true)
    @Documentation("Ид документа в БИС")
    private Long id;

    @XmlElement
    @Documentation("№ документа SAP")
    private String docNum;

    @XmlElement
    @Documentation("Дата документа")
    private Date docDate;

    @XmlElement
    @Documentation("Дата сторнирования документа")
    private Date revDate;
}

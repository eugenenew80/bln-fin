package bln.fin.soap.purchase;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RequisitionLine {
    private String reqNum;
    private String lineNum;

    private List<Item> items;
}

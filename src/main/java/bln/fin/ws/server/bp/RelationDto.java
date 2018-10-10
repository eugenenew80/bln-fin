package bln.fin.ws.server.bp;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Отношение")
public class RelationDto {

    @XmlElement
    @Documentation("IDOC")
    private String iDoc;

    @XmlElement(required = true)
    @Documentation("Тип отношения")
    private String relationType;

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("№ делового партнера с которым есть отношения")
    private String bpNumRel;
}

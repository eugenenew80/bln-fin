package bln.fin.ws.server.bp;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation", namespace = "http://bis.kegoc.kz/server")
@Documentation("Отношение")
public class RelationDto {

    @XmlElement(required = true)
    @Documentation("Тип отношения")
    private String relationType;

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    private String bpNum;
}

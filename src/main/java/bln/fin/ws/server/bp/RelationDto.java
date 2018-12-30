package bln.fin.ws.server.bp;

import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Поля является обязательным")
    private String relationType;

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    @NotNull(message = "Поля является обязательным")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("№ делового партнера с которым есть отношения")
    @NotNull(message = "Поля является обязательным")
    private String bpNumRel;
}

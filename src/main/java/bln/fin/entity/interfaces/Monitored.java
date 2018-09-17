package bln.fin.entity.interfaces;

import java.time.LocalDateTime;

public interface Monitored {
    Long getId();

    LocalDateTime getCreateDate();
    LocalDateTime getLastUpdateDate();

    void setCreateDate(LocalDateTime d);
    void setLastUpdateDate(LocalDateTime d);
}

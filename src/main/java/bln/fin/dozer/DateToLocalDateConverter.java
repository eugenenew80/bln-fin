package bln.fin.dozer;

import org.dozer.DozerConverter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateConverter extends DozerConverter<Date, LocalDate> {
    public DateToLocalDateConverter() {
        super(Date.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(Date source, LocalDate destination) {
        if (source==null) return null;
        return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public Date convertFrom(LocalDate source, Date destination) {
        if (source==null) return null;
        return java.sql.Date.valueOf(source);
    }
}

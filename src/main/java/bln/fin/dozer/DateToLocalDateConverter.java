package bln.fin.dozer;

import org.dozer.DozerConverter;

import java.time.LocalDate;
import java.util.Date;

import static bln.fin.common.Util.toDate;
import static bln.fin.common.Util.toLocalDate;

public class DateToLocalDateConverter extends DozerConverter<Date, LocalDate> {

    public DateToLocalDateConverter() {
        super(Date.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(Date source, LocalDate destination) {
        if (source==null) return null;
        return toLocalDate(source);
    }

    @Override
    public Date convertFrom(LocalDate source, Date destination) {
        if (source==null) return null;
        return toDate(source);
    }
}

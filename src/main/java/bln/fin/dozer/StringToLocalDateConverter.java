package bln.fin.dozer;

import org.dozer.DozerConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter extends DozerConverter<String, LocalDate> {
    public StringToLocalDateConverter() {
        super(String.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(String source, LocalDate destination) {
        if (source==null) return null;
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-mm-dd"));
    }

    @Override
    public String convertFrom(LocalDate source, String destination) {
        if (source==null) return null;
        return source.format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
    }
}

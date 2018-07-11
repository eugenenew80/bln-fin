package bln.fin.common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Util {
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

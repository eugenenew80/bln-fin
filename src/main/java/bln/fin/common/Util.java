package bln.fin.common;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate date) {
        GregorianCalendar cal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        try {

            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH),
                DatatypeConstants.FIELD_UNDEFINED
            );

            return xmlGregorianCalendar;
        }
        catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Throwable getCause(Throwable e) {
        Throwable cause = e;
        while (e.getCause()!=null)
            e = e.getCause();
        return e;
    }
}

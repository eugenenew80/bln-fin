package bln.fin.dozer;

import org.dozer.DozerConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalDateToXmlGregorianCalendarConverter extends DozerConverter<LocalDate, XMLGregorianCalendar> {
    public LocalDateToXmlGregorianCalendarConverter() {
        super(LocalDate.class, XMLGregorianCalendar.class);
    }

    @Override
    public XMLGregorianCalendar convertTo(LocalDate localDate, XMLGregorianCalendar xmlGregorianCalendar) {
        if (localDate == null) return null;

        DatatypeFactory factory = getFactory();
        if (factory == null) return null;

        GregorianCalendar cal = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return factory.newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
    }

    @Override
    public LocalDate convertFrom(XMLGregorianCalendar xmlCal, LocalDate localDate) {
        if (xmlCal == null) return null;
        return LocalDate.of(xmlCal.getYear(), xmlCal.getMonth()-1, xmlCal.getDay());
    }

    private DatatypeFactory getFactory() {
        DatatypeFactory factory = null;
        try {
            factory = DatatypeFactory.newInstance();
        }
        catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return factory;
    }
}

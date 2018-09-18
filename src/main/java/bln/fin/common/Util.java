package bln.fin.common;

import bln.fin.entity.interfaces.Monitored;
import bln.fin.ws.server.MessageDto;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
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

    public static MessageDto createErrorEmptyMessage() {
        MessageDto msg = new MessageDto();
        msg.setSystem("BIS");
        msg.setMsgType("W");
        msg.setMsgNum("1");
        msg.setSapId(null);
        msg.setMsg("Input data list is empty");
        return msg;
    }

    public static MessageDto createErrorLineMessage(String sapId, Exception e) {
        String err;
        Throwable cause = getCause(e);
        if (cause.getMessage()!=null)
            err = cause.getMessage();
        else
            err = cause.getClass().getCanonicalName();

        MessageDto msg = new MessageDto();
        msg.setSystem("BIS");
        msg.setMsgType("E");
        msg.setMsgNum("2");
        msg.setSapId(sapId);
        msg.setMsg(err);
        return msg;
    }

    public static MessageDto createSuccessLineMessage(String sapId, String id) {
        MessageDto msg;
        msg = new MessageDto();
        msg.setSystem("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        msg.setMsg("OK");
        msg.setId(id);
        msg.setSapId(sapId);
        return msg;
    }

    public static void addMonitoring(Monitored monitored) {
        if (monitored.getId() == null)  {
            monitored.setCreateDate(LocalDateTime.now());
            monitored.setLastUpdateDate(null);
        }

        if (monitored.getId() != null)
            monitored.setLastUpdateDate(LocalDateTime.now());
    }

}

package bln.fin.common;

import bln.fin.entity.interfaces.Monitored;
import bln.fin.ws.server.MessageDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
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

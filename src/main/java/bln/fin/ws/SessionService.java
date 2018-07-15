package bln.fin.ws;

import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface SessionService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    SoapSession createSession(SoapSession session);

    SoapSession createSession(String objectCode, DirectionEnum direction);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    SoapSession successSession(SoapSession session, Long recCount);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    SoapSession errorSession(SoapSession session, Exception e);
}

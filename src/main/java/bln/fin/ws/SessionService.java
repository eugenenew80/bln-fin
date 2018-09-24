package bln.fin.ws;

import bln.fin.entity.pi.Session;
import bln.fin.entity.enums.DirectionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface SessionService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Session createSession(Session session);

    Session createSession(String objectCode, DirectionEnum direction);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Session successSession(Session session, Long recCount);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Session errorSession(Session session, Exception e);
}

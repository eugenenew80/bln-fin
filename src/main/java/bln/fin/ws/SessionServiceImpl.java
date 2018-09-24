package bln.fin.ws;

import bln.fin.entity.pi.Session;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import bln.fin.repo.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepo sessionRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session createSession(Session session) {
        session = sessionRepo.save(session);
        return session;
    }

    @Override
    public Session createSession(String objectCode, DirectionEnum direction) {
        Session session = new Session();
        session.setObjectCode(objectCode);
        session.setDirection(direction);
        session.setStartDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.P);
        return createSession(session);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session successSession(Session session, Long recCount) {
        session.setEndDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.C);
        session.setRecCount(recCount);
        session = sessionRepo.save(session);
        return session;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session errorSession(Session session, Exception e) {
        session.setEndDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.E);
        String message = e.getMessage();
        if (message.length() > 300) message = message.substring(0, 300);
        session.setErrMsg(message !=null ? message : e.getClass().getCanonicalName());
        session = sessionRepo.save(session);
        return session;
    }
}

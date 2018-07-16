package bln.fin.ws;

import bln.fin.entity.SoapSession;
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
    public SoapSession createSession(SoapSession session) {
        session = sessionRepo.save(session);
        return session;
    }

    @Override
    public SoapSession createSession(String objectCode, DirectionEnum direction) {
        SoapSession session = new SoapSession();
        session.setObjectCode(objectCode);
        session.setDirection(direction);
        session.setStartDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.P);
        return createSession(session);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SoapSession successSession(SoapSession session, Long recCount) {
        session.setEndDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.C);
        session.setRecCount(recCount);
        session = sessionRepo.save(session);
        return session;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SoapSession errorSession(SoapSession session, Exception e) {
        session.setEndDate(LocalDateTime.now());
        session.setStatus(SessionStatusEnum.E);
        session.setErrMsg(e.getMessage().substring(0, 300));
        session = sessionRepo.save(session);
        return session;
    }
}

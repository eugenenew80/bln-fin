package bln.fin.ws.client.bems;

import bems.FileType;
import bems.ObjectFactory;
import bems.SIDEXRequestType;
import bems.SIDEXResponseType;
import bln.fin.entity.bems.BemsInterface;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.Session;
import bln.fin.repo.BemsInterfaceRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class BemsClientServiceImpl implements BemsClientService {
    private static final Logger logger = LoggerFactory.getLogger(BemsClientService.class);
    private static final String objectCode = "BEMS";
    private final WebServiceTemplate bemsServiceTemplate;
    private final BemsInterfaceRepo bemsInterfaceRepo;
    private final SessionService sessionService;

    @Override
    public void send() {
        List<BemsInterface> list = bemsInterfaceRepo.findAllByStatus(BatchStatusEnum.W);
        send(list);
    }

    public void send(List<BemsInterface> list) {
        if (list.isEmpty()) return;
        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        try {
            list = updateStatuses(list, session);
            SIDEXRequestType request = createRequest(list);
            SIDEXResponseType response = (SIDEXResponseType) bemsServiceTemplate.marshalSendAndReceive(request);

            sessionService.successSession(session, (long) list.size());
            updateStatuses(list, session);
        }
        catch (SoapFaultClientException e) {
            logger.error("Error during request, Fault Code: " + e.getFaultCode() + ", " + "Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
        catch (Exception e) {
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
    }

    private SIDEXRequestType createRequest(List<BemsInterface> list) {
        byte[] contents = null;

        Map<Pair<String, LocalDate>, List<BemsInterface>> map = list.stream()
            .collect(groupingBy(t -> Pair.of(t.getCode(), t.getMeteringDate().toLocalDate())));

        for (Pair<String, LocalDate> pair : map.keySet()) {
            List<BemsInterface> data = map.get(pair);
            String s = pair.getFirst() + "#" + pair.getSecond();
            Long[] values = new Long[24];
            for (BemsInterface d :data)
                values[d.getMeteringDate().getHour()] = d.getVal();

            for (int i=0; i<values.length; i++)
                s = s + "#" + values[i];

            logger.trace(s);
        }

        ObjectFactory factory = new ObjectFactory();
        SIDEXRequestType requestType = factory.createSIDEXRequestType();
        FileType fileType = factory.createFileType();
        fileType.setName("file.txt");
        fileType.setContent(contents);

        requestType.setDocument(fileType);
        requestType.setUsage("");
        return requestType;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<BemsInterface> updateStatuses(List<BemsInterface> list, Session session) {
        for (BemsInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return bemsInterfaceRepo.save(list);
    }
}

package bln.fin.ws.server.bp;

import bln.fin.entity.BusinessPartner;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import bln.fin.repo.BusinessPartnerRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.bp.BusinessPartnerService", portName = "BusinessPartnerPort", serviceName = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private final BusinessPartnerRepo businessPartnerRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createBusinessPartners(List<BusinessPartnerDto> list) {
        SoapSession session = sessionService.createSession("BP", DirectionEnum.IMPORT);

        List<BusinessPartner> businessPartnerList = new ArrayList<>();
        for (BusinessPartnerDto bpDto : list) {
            System.out.println(bpDto);
        }

        try {
            businessPartnerRepo.save(businessPartnerList);
            sessionService.successSession(session, (long) businessPartnerList.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return createResponse(session);
    }

    private List<MessageDto> createResponse(SoapSession session) {
        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");

        if (session.getStatus()==SessionStatusEnum.C) {
            msg.setMsgType("S");
            msg.setMsgNum("0");
            messages.add(msg);
        }

        if (session.getStatus()==SessionStatusEnum.E) {
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setMsg(session.getErrMsg());
            messages.add(msg);
        }

        return messages;
    }
}

package bln.fin.soap.bp;

import bln.fin.entity.BusinessPartner;
import bln.fin.repo.BusinessPartnerRepo;
import bln.fin.soap.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.bp.BusinessPartnerService", portName = "BusinessPartnerPort", serviceName = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private final BusinessPartnerRepo businessPartnerRepo;

    @Override
    public List<MessageDto> createBusinessPartners(List<BusinessPartnerDto> list) {

        List<BusinessPartner> businessPartnerList = new ArrayList<>();
        for (BusinessPartnerDto bpDto : list) {
            System.out.println(bpDto);
        }

        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        messages.add(msg);

        return messages;
    }
}

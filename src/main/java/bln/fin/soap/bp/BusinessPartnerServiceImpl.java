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

        List<MessageDto> msgs = new ArrayList<>();
        for (BusinessPartner businessPartner : businessPartnerList) {
            MessageDto msg = new MessageDto();
            msg.setStatus("S");
            msg.setId(businessPartner.getId());
            msg.setSapId(businessPartner.getErpBpNum());
            msg.setMsgNum("0");
            msg.setText("OK");
            msgs.add(msg);
        }

        return msgs;
    }
}

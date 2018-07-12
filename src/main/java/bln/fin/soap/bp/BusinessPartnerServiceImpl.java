package bln.fin.soap.bp;

import bln.fin.repo.BusinessPartnerRepo;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.bp.BusinessPartnerService", portName = "BusinessPartnerPort", serviceName = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private final BusinessPartnerRepo businessPartnerRepo;

    @Override
    public Message createBusinessPartners(List<BusinessPartnerDto> list) {

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}

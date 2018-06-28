package bln.fin.soap.req;

import bln.fin.entity.ReqLine;
import bln.fin.repo.ReqLineRepo;
import bln.fin.soap.Message;
import org.springframework.stereotype.Service;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
public class ReqServiceImpl implements ReqService {

    @Override
    public Message createReqs(@WebParam(name = "req") List<ReqLineDto> list) {

        for (ReqLineDto lineDto : list) {
            ReqLine reqLine = new ReqLine();
            reqLine.setReqNum(lineDto.getReqNum());
            reqLine.setPosNum(lineDto.getPosNum());
            reqLine.setPosName(lineDto.getPosName());
            reqLine.setCompanyCode(lineDto.getCompanyCode());
            reqLine.setAmount(lineDto.getAmount());
            reqLine.setCurrencyCode(lineDto.getCurrencyCode());
            reqLine.setExpectedDate(lineDto.getExpectedDate());
            reqLine.setQuantity(lineDto.getQuantity());
            reqLine.setUnit(lineDto.getUnit());
            reqLine.setUnlocked(lineDto.getUnlocked().equals("Y") ? true : false);
            reqLine.setDeleted(lineDto.getDeleted().equals("Y") ? true : false);
        }

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }


    private ReqLineRepo reqLineRepo;
}

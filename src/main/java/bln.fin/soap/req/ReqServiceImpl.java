package bln.fin.soap.req;

import bln.fin.entity.ReqItem;
import bln.fin.entity.ReqLine;
import bln.fin.repo.ReqLineRepo;
import bln.fin.soap.Message;
import org.springframework.stereotype.Service;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
public class ReqServiceImpl implements ReqService {

    @Override
    public Message createReqs(@WebParam(name = "req") List<ReqLineDto> list) {

        List<ReqLine> reqLines = new ArrayList<>();
        for (ReqLineDto reqLineDto : list) {
            ReqLine reqLine = reqLineRepo.findByReqNumAndPosNum(reqLineDto.getReqNum(), reqLineDto.getPosNum());
            if (reqLine == null)
                reqLine = new ReqLine();

            reqLine.setReqNum(reqLineDto.getReqNum());
            reqLine.setPosNum(reqLineDto.getPosNum());
            reqLine.setPosName(reqLineDto.getPosName());
            reqLine.setCompanyCode(reqLineDto.getCompanyCode());
            reqLine.setAmount(reqLineDto.getAmount());
            reqLine.setCurrencyCode(reqLineDto.getCurrencyCode());
            reqLine.setExpectedDate(reqLineDto.getExpectedDate());
            reqLine.setQuantity(reqLineDto.getQuantity());
            reqLine.setUnit(reqLineDto.getUnit());
            reqLine.setUnlocked(reqLineDto.getUnlocked().equals("Y") ? true : false);
            reqLine.setDeleted(reqLineDto.getDeleted().equals("Y") ? true : false);
            reqLines.add(reqLine);

            List<ReqItem> reqItems = reqLine.getItems();
            if (reqItems == null) {
                reqItems = new ArrayList<>();
                reqLine.setItems(new ArrayList<>());
            }

            for (ReqItem reqItem : reqItems) {
                ReqItemDto reqItemDto = reqLineDto.getItems().stream()
                    .filter(t -> t.getLineNum().equals(reqItem.getLineNum()))
                    .findFirst()
                    .orElseGet(null);

                if (reqItemDto==null)
                    reqItems.remove(reqItem);
            }

            for (ReqItemDto reqItemDto : reqLineDto.getItems()) {
                ReqItem reqItem = reqItems.stream()
                    .filter(t -> t.getLineNum().equals(reqItemDto.getLineNum()))
                    .findFirst()
                    .orElse(new ReqItem());

                if (reqItem.getId() == null)
                    reqItems.add(reqItem);

                reqItem.setLine(reqLine);
                reqItem.setItemNum(reqItemDto.getItemNum());
                reqItem.setItemName(reqItemDto.getItemName());
                reqItem.setLineNum(reqItemDto.getLineNum());
                reqItem.setPrice(reqItemDto.getPrice());
                reqItem.setQuantity(reqItemDto.getQuantity());
                reqItem.setPrice(reqItemDto.getPrice());
                reqItem.setUnit(reqItemDto.getUnit());
            }
        }
        reqLineRepo.save(reqLines);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(reqLines.size() + " records created");
        return msg;
    }

    private ReqLineRepo reqLineRepo;
}

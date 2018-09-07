package bln.fin.ws.server.req;

import bln.fin.entity.ReqLine;
import bln.fin.repo.ReqLineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReqBusinessServiceImpl implements ReqBusinessService {
    private final ReqLineRepo reqLineRepo;

    @Override
    public ReqLine createReqLine(ReqLineDto lineDto) {
        ReqLine reqLine = reqLineRepo.findByReqNumAndPosNum(lineDto.getReqNum(), lineDto.getPosNum());
        if (reqLine == null)
            reqLine = new ReqLine();

        reqLine.setReqNum(lineDto.getReqNum());
        reqLine.setPosNum(lineDto.getPosNum());
        reqLine.setPosName(lineDto.getPosName());
        reqLine.setCompanyCode(lineDto.getCompanyCode());
        reqLine.setAmount(lineDto.getQuantity() * lineDto.getPrice());
        reqLine.setCurrencyCode(lineDto.getCurrencyCode());
        reqLine.setExpectedDate(lineDto.getExpectedDate());
        reqLine.setQuantity(lineDto.getQuantity());
        reqLine.setUnit(lineDto.getUnit());
        reqLine.setPrice(lineDto.getPrice());
        reqLine.setItemNum(lineDto.getItemNum());

        if (lineDto.getUnlocked()!=null)
            reqLine.setUnlocked(lineDto.getUnlocked().equals("Y") ? true : false);

        if (lineDto.getDeleted()!=null)
            reqLine.setDeleted(lineDto.getDeleted().equals("Y") ? true : false);

        return reqLine;
    }
}

package bln.fin.soap.req;

import bln.fin.entity.ReqItem;
import bln.fin.entity.ReqLine;
import bln.fin.repo.ReqLineRepo;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        reqLine.setAmount(lineDto.getAmount());
        reqLine.setCurrencyCode(lineDto.getCurrencyCode());
        reqLine.setExpectedDate(lineDto.getExpectedDate());
        reqLine.setQuantity(lineDto.getQuantity());
        reqLine.setUnit(lineDto.getUnit());
        reqLine.setUnlocked(lineDto.getUnlocked().equals("Y") ? true : false);
        reqLine.setDeleted(lineDto.getDeleted().equals("Y") ? true : false);

        List<ReqItem> reqItems = reqLine.getItems();
        if (reqItems == null) {
            reqItems = new ArrayList<>();
            reqLine.setItems(reqItems);
        }

        for (int i = 0; i < reqItems.size(); i++) {
            ReqItem reqItem = reqItems.get(i);
            Optional<ReqItemDto> reqItemDto = lineDto.getItems().stream()
                .filter(t -> t.getRowNum().equals(reqItem.getRowNum()))
                .findFirst();

            if (!reqItemDto.isPresent())
                reqItems.remove(reqItem);
        }

        for (ReqItemDto reqItemDto : lineDto.getItems()) {
            ReqItem reqItem = reqItems.stream()
                .filter(t -> t.getRowNum().equals(reqItemDto.getRowNum()))
                .findFirst()
                .orElse(new ReqItem());

            if (reqItem.getId() == null)
                reqItems.add(reqItem);

            reqItem.setLine(reqLine);
            reqItem.setItemNum(reqItemDto.getItemNum());
            reqItem.setItemName(reqItemDto.getItemName());
            reqItem.setRowNum(reqItemDto.getRowNum());
            reqItem.setPrice(reqItemDto.getPrice());
            reqItem.setQuantity(reqItemDto.getQuantity());
            reqItem.setPrice(reqItemDto.getPrice());
            reqItem.setUnit(reqItemDto.getUnit());
        }

        return reqLine;
    }
}

package bln.fin.ws.client.contract;

import org.springframework.stereotype.Service;

@Service
public interface SaleContractService {
    void sendByBusinessPartner(Long bp1Id, Long bp2Id);
    void sendOne(Long contractId);
}

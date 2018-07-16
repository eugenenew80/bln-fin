package bln.fin.ws.client.contract;

import org.springframework.stereotype.Service;

@Service
public interface PurchaseContractService {
    void sendByVendor(Long vendorId, Long customerId);
    void sendByContract(Long contractId);
}

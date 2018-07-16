package bln.fin.ws.client.contract;

import org.springframework.stereotype.Service;

@Service
public interface SaleContractService {
    void sendByCustomer(Long vendorId, Long customerId);
    void sendByContract(Long contractId);
}

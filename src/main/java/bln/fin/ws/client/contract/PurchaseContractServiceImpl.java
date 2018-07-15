package bln.fin.ws.client.contract;

import bln.fin.repo.ContractRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseContractServiceImpl implements PurchaseContractService {
    private final ContractRepo contractRepo;

    @Override
    public void sendByVendor(Long vendorId, Long customerId) {
    }

    @Override
    public void sendByContract(Long contractId) {
    }

    @Override
    public void sendAll() {
    }
}

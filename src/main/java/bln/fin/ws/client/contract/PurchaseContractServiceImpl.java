package bln.fin.ws.client.contract;

import bln.fin.entity.ContractKeg;
import bln.fin.repo.ContractKegRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import sap.contract.mm.Contract;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseContractServiceImpl implements PurchaseContractService {
    private final WebServiceTemplate saleContractServiceTemplate;
    private final ContractKegRepo contractRepo;

    @Override
    public void sendByVendor(Long vendorId, Long customerId) {
    }

    @Override
    public void sendByContract(Long contractId) {
    }

    private Contract.Item createItem(ContractKeg contract) {
        Contract.Item item = new Contract.Item();

        return item;
    }

    private void updateContracts(List<ContractKeg> contracts) {

    }
}

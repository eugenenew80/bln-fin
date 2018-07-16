package bln.fin.ws.client.contract;

import bln.fin.entity.ContractKeg;
import bln.fin.repo.ContractKegRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.contract.sd.Contract;
import sap.contract.sd.ObjectFactory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class SaleContractServiceImpl implements SaleContractService {
    private final WebServiceTemplate saleContractServiceTemplate;
    private final ContractKegRepo contractRepo;

    @Override
    public void sendByBusinessPartner(Long bp1Id, Long bp2Id) {
        List<ContractKeg> contracts = contractRepo.findAllByBp1IdAndBp2Id(bp1Id, bp2Id);
        List<Contract.Item> items = createItems(contracts);
        if (items.isEmpty())
            return;

        Contract contractReq = new ObjectFactory().createContract();
        contractReq.getItem().addAll(items);
        request(contracts, contractReq);
    }


    @Override
    public void sendOne(Long contractId) {
        List<ContractKeg> contracts = Arrays.asList(contractRepo.findOne(contractId));
        List<Contract.Item> items = createItems(contracts);
        if (items.isEmpty())
            return;

        Contract contractReq = new ObjectFactory().createContract();
        contractReq.getItem().addAll(items);
        request(contracts, contractReq);
    }


    private void request(List<ContractKeg> contracts, Contract contractReq) {
        try {
            Object response = saleContractServiceTemplate.marshalSendAndReceive(contractReq);
            updateContracts(contracts);
        }
        catch (SoapFaultClientException e) {
            System.out.println("Fault Code: " + e.getFaultCode());
            System.out.println("Fault Reason: " + e.getFaultStringOrReason());
            System.out.println("Message: " + e.getLocalizedMessage());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Contract.Item> createItems(List<ContractKeg> contracts) {
        return contracts
            .stream()
            .filter(t -> t.getTransferredToErpDate()==null)
            .map(t -> createItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private Contract.Item createItem(ContractKeg contract) {
        Contract.Item item = new Contract.Item();
        return item;
    }

    private void updateContracts(List<ContractKeg> contracts) {
        for (ContractKeg contract: contracts)
            contract.setTransferredToErpDate(LocalDateTime.now());
        contractRepo.save(contracts);
    }
}

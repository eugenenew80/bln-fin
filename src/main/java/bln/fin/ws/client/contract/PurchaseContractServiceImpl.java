package bln.fin.ws.client.contract;

import bln.fin.repo.ContractInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@RequiredArgsConstructor
public class PurchaseContractServiceImpl implements PurchaseContractService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseContractService.class);
    private final ContractInterfaceRepo contractInterfaceRepo;
    private final WebServiceTemplate purchaseContractServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
    }
}

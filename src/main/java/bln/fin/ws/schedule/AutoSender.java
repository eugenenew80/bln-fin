package bln.fin.ws.schedule;

import bln.fin.ws.client.contract.mm.PurchaseContractClientService;
import bln.fin.ws.client.contract.sd.SaleContractClientService;
import bln.fin.ws.client.invoice.InvoiceClientService;
import bln.fin.ws.client.invoiceRev.InvoiceRevClientService;
import bln.fin.ws.client.plan.SalePlanClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoSender {
    private final SalePlanClientService salePlanClientService;
    private final InvoiceRevClientService invoiceRevClientService;
    private final InvoiceClientService invoiceClientService;
    private final PurchaseContractClientService purchaseContractClientService;
    private final SaleContractClientService saleContractClientService;

    @Scheduled(cron = "0 */1 * * * *")
    public void run() {
        /*
        salePlanClientService.send();
        invoiceClientService.send();
        invoiceRevClientService.send();
        saleContractClientService.send();
        purchaseContractClientService.send();
        */
    }
}

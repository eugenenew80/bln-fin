package bln.fin.ws.schedule;

import bln.fin.ws.client.invoiceRev.InvoiceRevClientService;
import bln.fin.ws.client.plan.SalePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoSender {
    private final SalePlanService salePlanService;
    private final InvoiceRevClientService invoiceRevClientService;

    @Scheduled(cron = "0 */1 * * * *")
    public void run() {
        invoiceRevClientService.send();
    }

}

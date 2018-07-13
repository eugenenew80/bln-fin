package bln.fin.ws.server.debt;

import bln.fin.entity.CheckApplication;
import bln.fin.entity.ReceiptApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DebtBusinessService {
    List<CheckApplication> createCheckApplication(DebtDto debtDto);

    List<ReceiptApplication> createReceiptApplication(DebtDto debtDto);
}

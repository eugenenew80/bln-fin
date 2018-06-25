package bln.fin.soap.debt.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DebtDto {
    private String businessPartnerType;
    private String debtType;
    private LocalDate accountingDate;
    private Double amount;
    private String currencyCode;
    private Double exchangeRate;
    private String docNum;
    private LocalDate docDate;
    private String businessPartnerNum;
    private String contractNum;
    private String externalContractNum;
    private String companyCode;
}

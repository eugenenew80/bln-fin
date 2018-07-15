package bln.fin.repo;

import bln.fin.entity.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleInvoiceRepo extends JpaRepository<SaleInvoice, Long> {
    SaleInvoice findByErpDocNumAndErpDocDate(String erpDocNum, LocalDate erpDocDate);
    List<SaleInvoice> findAllByContractId(Long contractId);
    List<SaleInvoice> findAllByVendorIdAndCustomerId(Long vendorId, Long customerId);
}

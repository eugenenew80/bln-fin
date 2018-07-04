package bln.fin.repo;

import bln.fin.entity.PurchaseInvoice;
import bln.fin.entity.ReceiptApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoice, Long> {
    PurchaseInvoice findByDocNumAndDocDate(String docNum, LocalDate docDate);
}

package bln.fin.repo;

import bln.fin.entity.ReceiptApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ReceiptApplicationRepo extends JpaRepository<ReceiptApplication, Long> {
    ReceiptApplication findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(String erpDocNum, LocalDate erpDocDate);
}

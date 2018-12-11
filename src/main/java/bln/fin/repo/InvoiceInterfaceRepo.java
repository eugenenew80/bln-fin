package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.InvoiceInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceInterfaceRepo extends JpaRepository<InvoiceInterface, Long> {
    List<InvoiceInterface> findByDocNumAndDocDate(String docNum, LocalDate docDate);
    List<InvoiceInterface> findAllByStatusAndBpType(BatchStatusEnum status, String bpType);

    @Procedure(name = "InvoiceInterface.updateStatuses")
    void updateStatuses();
}

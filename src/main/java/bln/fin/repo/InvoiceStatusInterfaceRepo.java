package bln.fin.repo;

import bln.fin.entity.pi.InvoiceStatusInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceStatusInterfaceRepo extends JpaRepository<InvoiceStatusInterface, Long> {
    List<InvoiceStatusInterface> findByDocNumAndDocDate(String docNum, LocalDate docDate);

    @Procedure(name = "InvoiceStatusInterface.transfer")
    void transfer();
}

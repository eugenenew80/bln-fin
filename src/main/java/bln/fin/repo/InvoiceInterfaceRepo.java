package bln.fin.repo;

import bln.fin.entity.pi.InvoiceInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceInterfaceRepo extends JpaRepository<InvoiceInterface, Long> {
    List<InvoiceInterface> findByDocNumAndDocDate(String docNum, LocalDate docDate);
}

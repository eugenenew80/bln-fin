package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.InvoiceRevInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRevInterfaceRepo extends JpaRepository<InvoiceRevInterface, Long> {
    List<InvoiceRevInterface> findAllByStatus(BatchStatusEnum status);
}

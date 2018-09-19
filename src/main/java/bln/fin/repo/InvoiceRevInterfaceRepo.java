package bln.fin.repo;

import bln.fin.entity.pi.InvoiceRevInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRevInterfaceRepo extends JpaRepository<InvoiceRevInterface, Long> { }

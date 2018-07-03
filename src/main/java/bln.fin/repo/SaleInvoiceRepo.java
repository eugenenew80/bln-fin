package bln.fin.repo;

import bln.fin.entity.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleInvoiceRepo extends JpaRepository<SaleInvoice, Long> { }

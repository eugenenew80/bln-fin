package bln.fin.repo;

import bln.fin.entity.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoice, Long> { }

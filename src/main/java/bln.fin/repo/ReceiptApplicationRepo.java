package bln.fin.repo;

import bln.fin.entity.ReceiptApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptApplicationRepo extends JpaRepository<ReceiptApplication, Long> { }

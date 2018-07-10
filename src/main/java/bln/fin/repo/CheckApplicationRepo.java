package bln.fin.repo;

import bln.fin.entity.CheckApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface CheckApplicationRepo extends JpaRepository<CheckApplication, Long> {
    CheckApplication findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(String docNum, LocalDate docDate);
}

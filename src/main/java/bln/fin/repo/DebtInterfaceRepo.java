package bln.fin.repo;

import bln.fin.entity.pi.DebtInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DebtInterfaceRepo extends JpaRepository<DebtInterface, Long> {
    List<DebtInterface> findByDocNumAndDocDate(String docNum, LocalDate docDate);

    @Procedure(name = "DebtInterface.transfer")
    void transfer();
}

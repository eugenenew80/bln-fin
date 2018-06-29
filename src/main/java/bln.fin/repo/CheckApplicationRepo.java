package bln.fin.repo;

import bln.fin.entity.CheckApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CheckApplicationRepo extends JpaRepository<CheckApplication, Long> {
    CheckApplication findByDocNumAndDocDate(String docNum, Date docDate);
}

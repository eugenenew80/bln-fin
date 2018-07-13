package bln.fin.repo;

import bln.fin.entity.SalePlanHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalePlanHeaderRepo extends JpaRepository<SalePlanHeader, Long> { }

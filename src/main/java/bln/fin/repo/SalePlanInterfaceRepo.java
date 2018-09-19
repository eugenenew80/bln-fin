package bln.fin.repo;

import bln.fin.entity.pi.SalePlanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalePlanInterfaceRepo extends JpaRepository<SalePlanInterface, Long> { }

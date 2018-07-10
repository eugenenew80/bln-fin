package bln.fin.repo;

import bln.fin.entity.Contract;
import bln.fin.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepo extends JpaRepository<Unit, Long> {
    Unit findByCode(String code);
}

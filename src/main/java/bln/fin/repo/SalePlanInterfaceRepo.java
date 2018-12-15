package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.SalePlanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalePlanInterfaceRepo extends JpaRepository<SalePlanInterface, Long> {
    List<SalePlanInterface> findAllByStatus(BatchStatusEnum status);

    @Procedure(name = "SalePlanInterface.updateStatuses")
    void updateStatuses();
}

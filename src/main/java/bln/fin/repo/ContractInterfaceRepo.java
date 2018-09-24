package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.ContractInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractInterfaceRepo extends JpaRepository<ContractInterface, Long> {
    List<ContractInterface> findAllByStatus(BatchStatusEnum status);
}
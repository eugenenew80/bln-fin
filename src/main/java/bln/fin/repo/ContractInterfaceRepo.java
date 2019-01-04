package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.ContractInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractInterfaceRepo extends JpaRepository<ContractInterface, Long> {

    @Query("select t from ContractInterface t where t.status = ?1 and t.bpType = ?2 order by t.contractId" )
    List<ContractInterface> findAllByStatus(BatchStatusEnum status, String bpType);

    @Procedure(name = "ContractInterface.updateStatuses")
    void updateStatuses();
}

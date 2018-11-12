package bln.fin.repo;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.pi.ContractInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractInterfaceRepo extends JpaRepository<ContractInterface, Long> {
    List<ContractInterface> findAllByStatusAndBpType(BatchStatusEnum status, String bpType);

    @Procedure(name = "sap_interface.contract_sd_update")
    void update();
}

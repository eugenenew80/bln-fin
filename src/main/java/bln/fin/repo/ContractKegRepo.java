package bln.fin.repo;

import bln.fin.entity.ContractKeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractKegRepo extends JpaRepository<ContractKeg, Long> {
    ContractKeg findByContractNum(String contractNum);
    List<ContractKeg> findAllByBp1IdAndBp2Id(Long bp1Id, Long bp2Id);
}

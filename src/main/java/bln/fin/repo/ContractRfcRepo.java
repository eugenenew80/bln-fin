package bln.fin.repo;

import bln.fin.entity.ContractKeg;
import bln.fin.entity.ContractRfc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRfcRepo extends JpaRepository<ContractRfc, Long> {
    ContractKeg findByContractNum(String contractNum);
    List<ContractKeg> findAllByBp1IdAndBp2Id(Long bp1Id, Long bp2Id);
}

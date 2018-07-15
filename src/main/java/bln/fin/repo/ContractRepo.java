package bln.fin.repo;

import bln.fin.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Long> {
    Contract findByContractNum(String contractNum);
    List<Contract> findAllByBp1IdAndBp2Id(Long bp1Id, Long bp2Id);
}

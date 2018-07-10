package bln.fin.repo;

import bln.fin.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Long> {
    Contract findByContractNum(String contractNum);
}

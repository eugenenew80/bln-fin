package bln.fin.repo;

import bln.fin.entity.pi.BpInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BpInterfaceRepo extends JpaRepository<BpInterface, Long> {
    List<BpInterface> findByBpNum(String bpNum);
}

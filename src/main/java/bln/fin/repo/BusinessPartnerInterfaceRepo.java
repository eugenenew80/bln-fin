package bln.fin.repo;

import bln.fin.entity.BusinessPartnerInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusinessPartnerInterfaceRepo extends JpaRepository<BusinessPartnerInterface, Long> {
    List<BusinessPartnerInterface> findByBpNum(String bpNum);
}

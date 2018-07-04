package bln.fin.repo;

import bln.fin.entity.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessPartnerRepo extends JpaRepository<BusinessPartner, Long> {
    BusinessPartner findByErpBpNum(String erpBpNum);
    BusinessPartner findByErpCompanyCode(String erpСьзфтнСщву);
}

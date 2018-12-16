package bln.fin.repo;

import bln.fin.entity.pi.BpRelationInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BpRelationInterfaceRepo extends JpaRepository<BpRelationInterface, Long> {
    List<BpRelationInterface> findByRelationTypeAndBpNumAndBpNumRel(String relationType, String bpNum, String bpNumRel);

    @Procedure(name = "BpRelationInterface.transfer")
    void transfer();
}

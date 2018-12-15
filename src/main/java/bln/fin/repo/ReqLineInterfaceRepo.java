package bln.fin.repo;

import bln.fin.entity.pi.ReqLineInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReqLineInterfaceRepo extends JpaRepository<ReqLineInterface, Long> {
    List<ReqLineInterface> findByReqNumAndPosNum(Long reqNum, Long posNum);

    @Procedure(name = "ReqLineInterface.updateStatuses")
    void updateStatuses();
}

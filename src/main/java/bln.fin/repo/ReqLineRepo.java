package bln.fin.repo;

import bln.fin.entity.ReqLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReqLineRepo extends JpaRepository<ReqLine, Long> {
    ReqLine findByReqNumAndPosNum(Long reqNum, Long posNum);
}

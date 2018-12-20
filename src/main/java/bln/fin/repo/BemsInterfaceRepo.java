package bln.fin.repo;

import bln.fin.entity.bems.BemsInterface;
import bln.fin.entity.enums.BatchStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BemsInterfaceRepo extends JpaRepository<BemsInterface, Long> {
    List<BemsInterface> findAllByStatus(BatchStatusEnum status);
}

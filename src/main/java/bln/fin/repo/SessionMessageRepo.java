package bln.fin.repo;

import bln.fin.entity.pi.SessionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionMessageRepo extends JpaRepository<SessionMessage, Long> { }

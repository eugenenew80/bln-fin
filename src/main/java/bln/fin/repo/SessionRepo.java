package bln.fin.repo;

import bln.fin.entity.SoapSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<SoapSession, Long> { }

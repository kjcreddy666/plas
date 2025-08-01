package in.zeta.academy.capstone.plas.repository;

import in.zeta.academy.capstone.plas.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByUserId(UUID userId);
}

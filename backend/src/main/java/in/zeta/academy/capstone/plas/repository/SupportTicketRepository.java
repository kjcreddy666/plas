package in.zeta.academy.capstone.plas.repository;

import in.zeta.academy.capstone.plas.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}

package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.TicketDetailsDto;
import in.zeta.academy.capstone.plas.dto.TicketRequestDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.SupportTicket;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.TicketStatus;
import in.zeta.academy.capstone.plas.exception.TicketNotFoundException;
import in.zeta.academy.capstone.plas.repository.SupportTicketRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final UserService userService;
    private final LoanApplicationService loanApplicationService;

    public SupportTicket createTicket( @Valid @NotNull TicketRequestDto dto) {
        Users user = userService.getUserById(dto.getUserId());
        LoanApplication loanApplication = loanApplicationService
                .getApplicationById(dto.getLoanApplicationId())
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        SupportTicket ticket = SupportTicket.builder()
                .user(user)
                .loanApplication(loanApplication)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .response("")
                .build();

        return supportTicketRepository.save(ticket);
    }

    public List<TicketDetailsDto> getTicketDetailsByUserId(UUID userId) {
        List<SupportTicket> tickets = supportTicketRepository.findByUserId(userId);

        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found for user ID: " + userId);
        }

        return tickets.stream()
                .map(ticket -> new TicketDetailsDto(
                        ticket.getId(),
                        ticket.getSubject(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticket.getResponse()
                ))
                .collect(Collectors.toList());
    }

    public TicketDetailsDto getTicketDetailsByTicketId(Long ticketId) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        return new TicketDetailsDto(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getResponse()
        );
    }

}



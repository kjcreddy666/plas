package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.TicketDetailsDto;
import in.zeta.academy.capstone.plas.dto.TicketRequestDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.SupportTicket;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.TicketStatus;
import in.zeta.academy.capstone.plas.exception.LoanNotFoundException;
import in.zeta.academy.capstone.plas.exception.TicketNotFoundException;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.SupportTicketRepository;
import in.zeta.academy.capstone.plas.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    public TicketDetailsDto createTicket(@Valid @NotNull TicketRequestDto dto) {
        Users user = userRepository.findById(dto.getUserId()).orElseThrow(()-> new UserNotFoundException("User not found with id: " + dto.getUserId()));
        LoanApplication loanApplication = loanApplicationRepository.findById(dto.getLoanApplicationId()).orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + dto.getLoanApplicationId()));

        SupportTicket ticket = SupportTicket.builder()
                .user(user)
                .loanApplication(loanApplication)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .response("")
                .build();

        SupportTicket savedTicket = supportTicketRepository.save(ticket);

        return new TicketDetailsDto(
                savedTicket.getId(),
                ticket.getLoanApplication().getId(),
                savedTicket.getSubject(),
                savedTicket.getDescription(),
                savedTicket.getStatus(),
                savedTicket.getCreatedAt(),
                savedTicket.getUpdatedAt(),
                savedTicket.getResponse()
        );
    }


    public List<TicketDetailsDto> getTicketDetailsByUserId(UUID userId) {
        List<SupportTicket> tickets = supportTicketRepository.findByUserId(userId);

        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found for user ID: " + userId);
        }

        return tickets.stream()
                .map(ticket -> new TicketDetailsDto(
                        ticket.getId(),
                        ticket.getLoanApplication().getId(),
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
                ticket.getLoanApplication().getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getResponse()
        );
    }

}



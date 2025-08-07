package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.AdminLoanApplicationDto;
import in.zeta.academy.capstone.plas.dto.AdminTicketDto;
import in.zeta.academy.capstone.plas.dto.AdminUserDto;
import in.zeta.academy.capstone.plas.dto.TicketResponseDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.SupportTicket;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.enums.Role;
import in.zeta.academy.capstone.plas.enums.TicketStatus;
import in.zeta.academy.capstone.plas.exception.LoanNotFoundException;
import in.zeta.academy.capstone.plas.exception.TicketNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.SupportTicketRepository;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final LoanApplicationRepository loanApplicationRepository;

    private final SupportTicketRepository supportTicketRepository;

    private final UserRepository userRepository;

    public List<AdminLoanApplicationDto> getPendingLoanApplications() {
        List<LoanApplication> loanApplications = loanApplicationRepository.findByStatusIn(
                List.of(LoanApplicationStatus.NEW.name(), LoanApplicationStatus.UNDER_REVIEW.name()));
        return loanApplications.stream()
                .map(loan -> new AdminLoanApplicationDto(
                        loan.getId(),
                        loan.getUser().getId(),
                        loan.getAmount(),
                        loan.getTenureMonths(),
                        loan.getIncome(),
                        loan.getCreditScore(),
                        loan.getStatus(),
                        loan.getApplicationDate(),
                        loan.getPurpose(),
                        loan.getReviewRemarks()))
                .toList();
    }

    public List<AdminLoanApplicationDto> getFilteredLoanApplications(List<LoanApplicationStatus> statuses) {
        List<LoanApplication> loanApplications =  loanApplicationRepository.findByStatusIn(
                statuses.stream().map(LoanApplicationStatus::name).toList());
        return loanApplications.stream()
                .map(loan -> new AdminLoanApplicationDto(
                        loan.getId(),
                        loan.getUser().getId(),
                        loan.getAmount(),
                        loan.getTenureMonths(),
                        loan.getIncome(),
                        loan.getCreditScore(),
                        loan.getStatus(),
                        loan.getApplicationDate(),
                        loan.getPurpose(),
                        loan.getReviewRemarks()))
                .toList();
    }

    public LoanApplication updateLoanStatus(Long loanId, LoanApplicationStatus status, String remarks, LocalDateTime now) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan application not found"));

        loanApplication.setStatus(status);
        loanApplication.setReviewRemarks(remarks);
        Users admin = userRepository.findByRole(Role.ADMIN);
        loanApplication.setReviewedBy(admin);
        loanApplication.setReviewedAt(now);

        return loanApplicationRepository.save(loanApplication);
    }

    public List<AdminLoanApplicationDto> getAllLoanApplications() {
        List<LoanApplication> loanApplications = loanApplicationRepository.findAll();
        return loanApplications.stream()
                .map(loan -> new AdminLoanApplicationDto(
                        loan.getId(),
                        loan.getUser().getId(),
                        loan.getAmount(),
                        loan.getTenureMonths(),
                        loan.getIncome(),
                        loan.getCreditScore(),
                        loan.getStatus(),
                        loan.getApplicationDate(),
                        loan.getPurpose(),
                        loan.getReviewRemarks()))
                .toList();
    }

    public List<AdminTicketDto> getAllTickets() {
        List<SupportTicket> tickets = supportTicketRepository.findAll();
        return tickets.stream()
                .map(ticket -> new AdminTicketDto(
                        ticket.getId(),
                        ticket.getUser().getId(),
                        ticket.getLoanApplication() != null ? ticket.getLoanApplication().getId() : null,
                        ticket.getSubject(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticket.getResponse()))
                .toList();
    }

    public List<AdminTicketDto> autoCloseResolvedTickets() {
        List<SupportTicket> tickets = supportTicketRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (SupportTicket ticket : tickets) {
            if (ticket.getStatus() == TicketStatus.RESOLVED) {
                if (ticket.getUpdatedAt() != null && ticket.getUpdatedAt().isBefore(now.minusHours(24))) {
                    ticket.setStatus(TicketStatus.CLOSED);
                    ticket.setUpdatedAt(now);
                    supportTicketRepository.save(ticket);
                }
            }
        }

        // Return the latest list of AdminTicketDto
        return supportTicketRepository.findAll().stream()
                .map(ticket -> new AdminTicketDto(
                        ticket.getId(),
                        ticket.getUser().getId(),
                        ticket.getLoanApplication() != null ? ticket.getLoanApplication().getId() : null,
                        ticket.getSubject(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticket.getResponse()))
                .toList();
    }


    public SupportTicket updateTicketStatus(Long ticketId, String status, String response, LocalDateTime now) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Support ticket not found"));

        ticket.setStatus(TicketStatus.valueOf(status));
        ticket.setResponse(response);
        ticket.setUpdatedAt(now);

        return supportTicketRepository.save(ticket);
    }

    public Page<AdminUserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAllByRoleNot(Role.ADMIN, pageable)
                .map(user -> new AdminUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getMobile(),
                        user.getAddress(),
                        user.getRole()
                ));
    }

    public List<TicketResponseDto> getFilteredSupportTickets(List<TicketStatus> statuses) {
        List<SupportTicket> tickets = supportTicketRepository.findByStatusIn(
                statuses.stream().map(TicketStatus::name).toList());

        return tickets.stream()
                .map(ticket -> new TicketResponseDto(
                        ticket.getId(),
                        ticket.getLoanApplication() != null ? ticket.getLoanApplication().getId() : null,
                        ticket.getSubject(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticket.getResponse()
                ))
                .toList();
    }

}
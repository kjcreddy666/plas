package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.*;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.SupportTicket;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.enums.Role;
import in.zeta.academy.capstone.plas.enums.TicketStatus;
import in.zeta.academy.capstone.plas.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/loans/filter")
    public ResponseEntity<List<AdminLoanApplicationDto>> getFilteredApplications(@RequestParam List<LoanApplicationStatus> statuses) {
        List<AdminLoanApplicationDto> filteredApplications = adminService.getFilteredLoanApplications(statuses);
        if (filteredApplications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filteredApplications);
    }

    @PutMapping("/loan/{loanId}/status")
    public ResponseEntity<HttpStatus> updateLoanStatus(@PathVariable Long loanId,
                                                       @RequestBody LoanUpdationRequestDto request) {
        LoanApplication updated = adminService.updateLoanStatus(loanId, request.getStatus(), request.getRemarks(), LocalDateTime.now());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/loans")
    public ResponseEntity<List<AdminLoanApplicationDto>> getAllLoanApplications() {
        List<AdminLoanApplicationDto> allApplications = adminService.getAllLoanApplications();
        if (allApplications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allApplications);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<AdminTicketDto>> getSupportTickets() {
        List<AdminTicketDto> tickets = adminService.getAllTickets();
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<HttpStatus> updateTicketStatus(@PathVariable Long ticketId,
                                                         @RequestBody TicketUpdationRequestDto request) {
        SupportTicket updatedTicket = adminService.updateTicketStatus(ticketId, TicketStatus.RESOLVED.name(), request.getResponse(), LocalDateTime.now());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<AdminUserDto>> getAllUsers(Pageable pageable) {
        Page<AdminUserDto> usersPage = adminService.getAllUsers(pageable);
        if (usersPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usersPage);
    }

}
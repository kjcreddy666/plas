package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.TicketResponseDto;
import in.zeta.academy.capstone.plas.dto.TicketRequestDto;
import in.zeta.academy.capstone.plas.service.SupportTicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/support")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @PostMapping("/create")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto dto) {
        // Create the ticket and return the response
        TicketResponseDto ticket = supportTicketService.createTicket(dto);
        return ResponseEntity.ok(ticket);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponseDto>> getTicketByUserId(@PathVariable UUID userId) {
        // Get the tickets for the user
        List<TicketResponseDto> tickets = supportTicketService.getTicketDetailsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long ticketId) {
        // Get the ticket by ID
        TicketResponseDto ticket = supportTicketService.getTicketDetailsByTicketId(ticketId);
        return ResponseEntity.ok(ticket);
    }
}

package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.TicketDetailsDto;
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
@RequestMapping("/support")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @PostMapping("/create")
    public ResponseEntity<TicketDetailsDto> createTicket(@Valid @RequestBody TicketRequestDto dto) {
        TicketDetailsDto ticket = supportTicketService.createTicket(dto);
        return ResponseEntity.ok(ticket);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDetailsDto>> getTicketByUserId(@PathVariable UUID userId) {
        List<TicketDetailsDto> tickets = supportTicketService.getTicketDetailsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDetailsDto> getTicketById(@PathVariable Long ticketId) {
        TicketDetailsDto ticket = supportTicketService.getTicketDetailsByTicketId(ticketId);
        return ResponseEntity.ok(ticket);
    }
}

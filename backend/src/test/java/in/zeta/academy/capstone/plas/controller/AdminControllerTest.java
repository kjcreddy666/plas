package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.*;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.enums.TicketStatus;
import in.zeta.academy.capstone.plas.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredApplications() {
        List<AdminLoanApplicationDto> mockLoans = List.of(new AdminLoanApplicationDto(1L, java.util.UUID.randomUUID(), 10000.0, 12, 30000.0, 750, LoanApplicationStatus.NEW, null, "Education", null));
        when(adminService.getFilteredLoanApplications(List.of(LoanApplicationStatus.NEW)))
                .thenReturn(mockLoans);

        ResponseEntity<List<AdminLoanApplicationDto>> response = adminController.getFilteredApplications(List.of(LoanApplicationStatus.NEW));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(adminService, times(1)).getFilteredLoanApplications(List.of(LoanApplicationStatus.NEW));
    }

    @Test
    void testGetFilteredApplications_NoContent() {
        when(adminService.getFilteredLoanApplications(List.of(LoanApplicationStatus.NEW))).thenReturn(List.of());

        ResponseEntity<List<AdminLoanApplicationDto>> response = adminController.getFilteredApplications(List.of(LoanApplicationStatus.NEW));

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateLoanStatus() {
        LoanUpdationRequestDto requestDto = new LoanUpdationRequestDto(LoanApplicationStatus.APPROVED, "Approved");
        when(adminService.updateLoanStatus(eq(1L), eq(LoanApplicationStatus.APPROVED), eq("Approved"), any()))
                .thenReturn(null);

        ResponseEntity<HttpStatus> response = adminController.updateLoanStatus(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).updateLoanStatus(eq(1L), eq(LoanApplicationStatus.APPROVED), eq("Approved"), any());
    }

    @Test
    void testGetAllLoanApplications() {
        List<AdminLoanApplicationDto> mockLoans = List.of(new AdminLoanApplicationDto(1L, java.util.UUID.randomUUID(), 10000.0, 12, 30000.0, 750, LoanApplicationStatus.NEW, null, "Education", null));
        when(adminService.getAllLoanApplications()).thenReturn(mockLoans);

        ResponseEntity<List<AdminLoanApplicationDto>> response = adminController.getAllLoanApplications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetSupportTickets() {
        List<AdminTicketDto> tickets = List.of(new AdminTicketDto(1L, java.util.UUID.randomUUID(), 3L, "Subject", "Description", null, null, null, null));
        when(adminService.autoCloseResolvedTickets()).thenReturn(tickets);

        ResponseEntity<List<AdminTicketDto>> response = adminController.getSupportTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testUpdateTicketStatus() {
        TicketUpdationRequestDto requestDto = new TicketUpdationRequestDto("Response text");
        when(adminService.updateTicketStatus(eq(1L), eq("RESOLVED"), eq("Response text"), any())).thenReturn(null);

        ResponseEntity<HttpStatus> response = adminController.updateTicketStatus(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).updateTicketStatus(eq(1L), eq("RESOLVED"), eq("Response text"), any());
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        when(adminService.getAllUsers(pageable)).thenReturn(new PageImpl<>(List.of(
                new AdminUserDto(java.util.UUID.randomUUID(), "John", "john@example.com", 1234567890L, "Address", null)
        )));

        ResponseEntity<?> response = adminController.getAllUsers(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllLoanApplications_NoContent() {
        when(adminService.getAllLoanApplications()).thenReturn(List.of());

        ResponseEntity<List<AdminLoanApplicationDto>> response = adminController.getAllLoanApplications();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Covers the NO_CONTENT branch for getAllUsers
    @Test
    void testGetAllUsers_NoContent() {
        Pageable pageable = PageRequest.of(0, 10);
        when(adminService.getAllUsers(pageable)).thenReturn(new PageImpl<>(List.of()));

        ResponseEntity<?> response = adminController.getAllUsers(pageable);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Covers the NO_CONTENT branch for getSupportTickets
    @Test
    void testGetSupportTickets_NoContent() {
        when(adminService.getAllTickets()).thenReturn(List.of());

        ResponseEntity<List<AdminTicketDto>> response = adminController.getSupportTickets();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test for non-empty result
    @Test
    void testGetFilteredSupportTickets() {
        List<TicketResponseDto> mockTickets = List.of(
                new TicketResponseDto(1L,1234L, "Subject", "Description", TicketStatus.OPEN, null, null, null)
        );
        when(adminService.getFilteredSupportTickets(List.of(TicketStatus.OPEN, TicketStatus.CLOSED)))
                .thenReturn(mockTickets);

        ResponseEntity<List<TicketResponseDto>> response = adminController.getFilteredSupportTickets(
                List.of(TicketStatus.OPEN, TicketStatus.CLOSED)
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(adminService, times(1)).getFilteredSupportTickets(List.of(TicketStatus.OPEN, TicketStatus.CLOSED));
    }

    // Test for empty result
    @Test
    void testGetFilteredSupportTickets_NoContent() {
        when(adminService.getFilteredSupportTickets(List.of(TicketStatus.OPEN, TicketStatus.CLOSED)))
                .thenReturn(List.of());

        ResponseEntity<List<TicketResponseDto>> response = adminController.getFilteredSupportTickets(
                List.of(TicketStatus.OPEN, TicketStatus.CLOSED)
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

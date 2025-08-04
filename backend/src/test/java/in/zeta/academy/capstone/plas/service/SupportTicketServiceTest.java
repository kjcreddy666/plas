package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.TicketRequestDto;
import in.zeta.academy.capstone.plas.dto.TicketResponseDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupportTicketServiceTest {

    @Mock
    private SupportTicketRepository supportTicketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private SupportTicketService supportTicketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket_shouldCreateSuccessfully() {
        UUID userId = UUID.randomUUID();
        Long loanApplicationId = 1L;

        TicketRequestDto dto = new TicketRequestDto();
        dto.setUserId(userId);
        dto.setLoanApplicationId(loanApplicationId);
        dto.setSubject("Test Subject");
        dto.setDescription("Test Description");

        Users user = Users.builder().id(userId).build();
        LoanApplication loanApplication = LoanApplication.builder().id(loanApplicationId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanApplicationRepository.findById(loanApplicationId)).thenReturn(Optional.of(loanApplication));

        SupportTicket savedTicket = SupportTicket.builder()
                .id(1L)
                .user(user)
                .loanApplication(loanApplication)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .response("")
                .build();

        when(supportTicketRepository.save(any())).thenReturn(savedTicket);

        TicketResponseDto response = supportTicketService.createTicket(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Subject", response.getSubject());
        verify(supportTicketRepository, times(1)).save(any());
    }

    @Test
    void createTicket_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        Long loanApplicationId = 1L;

        TicketRequestDto dto = new TicketRequestDto();
        dto.setUserId(userId);
        dto.setLoanApplicationId(loanApplicationId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> supportTicketService.createTicket(dto));
    }

    @Test
    void createTicket_shouldThrowIfLoanApplicationNotFound() {
        UUID userId = UUID.randomUUID();
        Long loanApplicationId = 1L;

        TicketRequestDto dto = new TicketRequestDto();
        dto.setUserId(userId);
        dto.setLoanApplicationId(loanApplicationId);

        Users user = Users.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanApplicationRepository.findById(loanApplicationId)).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> supportTicketService.createTicket(dto));
    }

    @Test
    void getTicketDetailsByUserId_shouldReturnList() {
        UUID userId = UUID.randomUUID();

        SupportTicket ticket = SupportTicket.builder()
                .id(1L)
                .user(Users.builder().id(userId).build())
                .loanApplication(LoanApplication.builder().id(1L).build())
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .subject("Test Subject")
                .description("Test Description")
                .response("")
                .build();

        when(supportTicketRepository.findByUserId(userId)).thenReturn(List.of(ticket));

        List<TicketResponseDto> result = supportTicketService.getTicketDetailsByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("Test Subject", result.get(0).getSubject());
    }

    @Test
    void getTicketDetailsByUserId_shouldThrowIfNoTicketsFound() {
        UUID userId = UUID.randomUUID();

        when(supportTicketRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(TicketNotFoundException.class, () -> supportTicketService.getTicketDetailsByUserId(userId));
    }

    @Test
    void getTicketDetailsByTicketId_shouldReturnTicket() {
        Long ticketId = 1L;

        SupportTicket ticket = SupportTicket.builder()
                .id(ticketId)
                .loanApplication(LoanApplication.builder().id(1L).build())
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .subject("Test Subject")
                .description("Test Description")
                .response("")
                .build();

        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        TicketResponseDto result = supportTicketService.getTicketDetailsByTicketId(ticketId);

        assertNotNull(result);
        assertEquals("Test Subject", result.getSubject());
    }

    @Test
    void getTicketDetailsByTicketId_shouldThrowIfNotFound() {
        Long ticketId = 1L;

        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> supportTicketService.getTicketDetailsByTicketId(ticketId));
    }
}
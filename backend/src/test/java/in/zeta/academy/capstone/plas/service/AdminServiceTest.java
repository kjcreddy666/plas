package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.AdminLoanApplicationDto;
import in.zeta.academy.capstone.plas.dto.AdminTicketDto;
import in.zeta.academy.capstone.plas.dto.AdminUserDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    @Mock
    private SupportTicketRepository supportTicketRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private Users user;
    private Users admin;
    private LoanApplication loanApplication;
    private SupportTicket supportTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setName("User1");
        user.setEmail("user1@email.com");
        user.setMobile(1234567890L);
        user.setAddress("Address1");
        user.setRole(Role.CUSTOMER);

        admin = new Users();
        admin.setId(UUID.randomUUID());
        admin.setName("Admin");
        admin.setRole(Role.ADMIN);

        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setUser(user);
        loanApplication.setAmount(10000.0);
        loanApplication.setTenureMonths(12);
        loanApplication.setIncome(50000.0);
        loanApplication.setCreditScore(750);
        loanApplication.setStatus(LoanApplicationStatus.NEW);
        loanApplication.setApplicationDate(LocalDate.now());
        loanApplication.setPurpose("Home");
        loanApplication.setReviewRemarks("Pending");

        supportTicket = new SupportTicket();
        supportTicket.setId(1L);
        supportTicket.setUser(user);
        supportTicket.setLoanApplication(loanApplication);
        supportTicket.setSubject("Subject");
        supportTicket.setDescription("Description");
        supportTicket.setStatus(TicketStatus.OPEN);
        supportTicket.setCreatedAt(LocalDateTime.now().minusDays(1));
        supportTicket.setUpdatedAt(LocalDateTime.now());
        supportTicket.setResponse("Response");
    }

    @Test
    void getPendingLoanApplications_returnsList() {
        when(loanApplicationRepository.findByStatusIn(anyList()))
                .thenReturn(List.of(loanApplication));
        List<AdminLoanApplicationDto> result = adminService.getPendingLoanApplications();
        assertEquals(1, result.size());
        assertEquals(loanApplication.getId(), result.get(0).getLoanId());
    }

    @Test
    void getPendingLoanApplications_returnsEmptyList() {
        when(loanApplicationRepository.findByStatusIn(anyList()))
                .thenReturn(Collections.emptyList());
        List<AdminLoanApplicationDto> result = adminService.getPendingLoanApplications();
        assertTrue(result.isEmpty());
    }

    @Test
    void getFilteredLoanApplications_returnsList() {
        when(loanApplicationRepository.findByStatusIn(anyList()))
                .thenReturn(List.of(loanApplication));
        List<AdminLoanApplicationDto> result = adminService.getFilteredLoanApplications(
                List.of(LoanApplicationStatus.NEW));
        assertEquals(1, result.size());
        assertEquals(loanApplication.getId(), result.get(0).getLoanId());
    }

    @Test
    void getFilteredLoanApplications_returnsEmptyList() {
        when(loanApplicationRepository.findByStatusIn(anyList()))
                .thenReturn(Collections.emptyList());
        List<AdminLoanApplicationDto> result = adminService.getFilteredLoanApplications(
                List.of(LoanApplicationStatus.NEW));
        assertTrue(result.isEmpty());
    }

    @Test
    void updateLoanStatus_success() {
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(admin);
        when(loanApplicationRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        LoanApplication updated = adminService.updateLoanStatus(1L, LoanApplicationStatus.APPROVED, "Approved", LocalDateTime.now());
        assertEquals(LoanApplicationStatus.APPROVED, updated.getStatus());
        assertEquals("Approved", updated.getReviewRemarks());
        assertEquals(admin, updated.getReviewedBy());
    }

    @Test
    void updateLoanStatus_loanNotFound_throwsException() {
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LoanNotFoundException.class, () ->
                adminService.updateLoanStatus(1L, LoanApplicationStatus.REJECTED, "Rejected", LocalDateTime.now()));
    }

    @Test
    void getAllLoanApplications_returnsList() {
        when(loanApplicationRepository.findAll()).thenReturn(List.of(loanApplication));
        List<AdminLoanApplicationDto> result = adminService.getAllLoanApplications();
        assertEquals(1, result.size());
        assertEquals(loanApplication.getId(), result.get(0).getLoanId());
    }

    @Test
    void getAllLoanApplications_returnsEmptyList() {
        when(loanApplicationRepository.findAll()).thenReturn(Collections.emptyList());
        List<AdminLoanApplicationDto> result = adminService.getAllLoanApplications();
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllTickets_returnsList() {
        when(supportTicketRepository.findAll()).thenReturn(List.of(supportTicket));
        List<AdminTicketDto> result = adminService.getAllTickets();
        assertEquals(1, result.size());
        assertEquals(supportTicket.getId(), result.get(0).getId());
    }

    @Test
    void getAllTickets_returnsEmptyList() {
        when(supportTicketRepository.findAll()).thenReturn(Collections.emptyList());
        List<AdminTicketDto> result = adminService.getAllTickets();
        assertTrue(result.isEmpty());
    }

    @Test
    void updateTicketStatus_success() {
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.of(supportTicket));
        when(supportTicketRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        SupportTicket updated = adminService.updateTicketStatus(1L, TicketStatus.RESOLVED.name(), "Done", LocalDateTime.now());
        assertEquals(TicketStatus.RESOLVED, updated.getStatus());
        assertEquals("Done", updated.getResponse());
    }

    @Test
    void updateTicketStatus_ticketNotFound_throwsException() {
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () ->
                adminService.updateTicketStatus(1L, "CLOSED", "Done", LocalDateTime.now()));
    }

    @Test
    void updateTicketStatus_invalidStatus_throwsException() {
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.of(supportTicket));
        assertThrows(IllegalArgumentException.class, () ->
                adminService.updateTicketStatus(1L, "NOT_A_STATUS", "Done", LocalDateTime.now()));
    }

    @Test
    void getAllUsers_returnsPagedUsers() {
        Users user2 = new Users();
        user2.setId(UUID.randomUUID());
        user2.setName("User2");
        user2.setEmail("user2@email.com");
        user2.setMobile(9876543210L);
        user2.setAddress("Address2");
        user2.setRole(Role.CUSTOMER);

        Page<Users> usersPage = new PageImpl<>(List.of(user, user2));
        when(userRepository.findAllByRoleNot(eq(Role.ADMIN), any(Pageable.class))).thenReturn(usersPage);

        Page<AdminUserDto> result = adminService.getAllUsers(PageRequest.of(0, 10));
        assertEquals(2, result.getContent().size());
        assertEquals(user.getName(), result.getContent().get(0).getName());
    }

    @Test
    void getAllUsers_returnsEmptyPage() {
        Page<Users> usersPage = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAllByRoleNot(eq(Role.ADMIN), any(Pageable.class))).thenReturn(usersPage);

        Page<AdminUserDto> result = adminService.getAllUsers(PageRequest.of(0, 10));
        assertTrue(result.getContent().isEmpty());
    }
}
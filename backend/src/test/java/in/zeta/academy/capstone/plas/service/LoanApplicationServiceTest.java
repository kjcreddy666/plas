package in.zeta.academy.capstone.plas.service;
import in.zeta.academy.capstone.plas.dto.LoanRequestDto;
import in.zeta.academy.capstone.plas.dto.LoanResponseDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void applyForLoan_shouldApplySuccessfully() {
        UUID userId = UUID.randomUUID();
        LoanRequestDto dto = LoanRequestDto.builder()
                .userId(userId.toString())
                .amount(50000.0)
                .tenureMonths(12)
                .income(30000.0)
                .creditScore(750)
                .purpose("Medical")
                .build();

        Users user = Users.builder().id(userId).build();
        when(loanApplicationRepository.findByUserIdAndApplicationDate(eq(userId), any())).thenReturn(Collections.emptyList());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        LoanApplication savedApplication = LoanApplication.builder()
                .id(1L)
                .user(user)
                .amount(dto.getAmount())
                .tenureMonths(dto.getTenureMonths())
                .income(dto.getIncome())
                .creditScore(dto.getCreditScore())
                .status(LoanApplicationStatus.NEW)
                .applicationDate(LocalDate.now())
                .purpose(dto.getPurpose())
                .build();

        when(loanApplicationRepository.save(any())).thenReturn(savedApplication);

        LoanResponseDto response = loanApplicationService.applyForLoan(dto);

        assertNotNull(response);
        assertEquals(50000.0, response.getAmount());
        verify(loanApplicationRepository, times(1)).save(any());
    }

    @Test
    void applyForLoan_shouldThrowIfIncomeLow() {
        LoanRequestDto dto = LoanRequestDto.builder()
                .userId(UUID.randomUUID().toString())
                .income(20000.0)
                .creditScore(700)
                .build();

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.applyForLoan(dto));
    }

    @Test
    void applyForLoan_shouldThrowIfCreditScoreInvalid() {
        LoanRequestDto dto = LoanRequestDto.builder()
                .userId(UUID.randomUUID().toString())
                .income(30000.0)
                .creditScore(250)
                .build();

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.applyForLoan(dto));
    }

    @Test
    void applyForLoan_shouldThrowIfAlreadyAppliedToday() {
        UUID userId = UUID.randomUUID();
        LoanRequestDto dto = LoanRequestDto.builder()
                .userId(userId.toString())
                .income(30000.0)
                .creditScore(700)
                .build();

        when(loanApplicationRepository.findByUserIdAndApplicationDate(eq(userId), any())).thenReturn(List.of(new LoanApplication()));

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.applyForLoan(dto));
    }

    @Test
    void applyForLoan_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        LoanRequestDto dto = LoanRequestDto.builder()
                .userId(userId.toString())
                .income(30000.0)
                .creditScore(700)
                .build();

        when(loanApplicationRepository.findByUserIdAndApplicationDate(eq(userId), any())).thenReturn(Collections.emptyList());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> loanApplicationService.applyForLoan(dto));
    }

    @Test
    void getApplicationsByUser_shouldReturnList() {
        UUID userId = UUID.randomUUID();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .user(Users.builder().id(userId).build())
                .amount(50000.0)
                .tenureMonths(12)
                .income(30000.0)
                .creditScore(750)
                .status(LoanApplicationStatus.NEW)
                .applicationDate(LocalDate.now())
                .purpose("Car")
                .build();

        when(loanApplicationRepository.findByUserId(userId)).thenReturn(List.of(loan));

        List<LoanResponseDto> result = loanApplicationService.getApplicationsByUser(userId);
        assertEquals(1, result.size());
    }

    @Test
    void getApplicationById_shouldReturnLoanResponse() {
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .amount(50000.0)
                .tenureMonths(12)
                .income(30000.0)
                .creditScore(750)
                .status(LoanApplicationStatus.NEW)
                .applicationDate(LocalDate.now())
                .purpose("Education")
                .build();

        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loan));

        Optional<LoanResponseDto> result = loanApplicationService.getApplicationById(1L);
        assertTrue(result.isPresent());
        assertEquals(50000.0, result.get().getAmount());
    }

    @Test
    void getApplicationById_shouldReturnEmptyIfNotFound() {
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<LoanResponseDto> result = loanApplicationService.getApplicationById(1L);
        assertTrue(result.isEmpty());
    }
}
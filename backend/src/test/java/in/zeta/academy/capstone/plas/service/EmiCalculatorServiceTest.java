package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.RepaymentScheduleResponseDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.repayment_schedule;
import in.zeta.academy.capstone.plas.exception.LoanNotFoundException;
import in.zeta.academy.capstone.plas.repository.EmiRepository;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmiCalculatorServiceTest {

    private EmiRepository emiRepository;
    private LoanApplicationRepository loanApplicationRepository;
    private EmiCalculatorService emiCalculatorService;

    @BeforeEach
    void setUp() {
        emiRepository = mock(EmiRepository.class);
        loanApplicationRepository = mock(LoanApplicationRepository.class);
        emiCalculatorService = new EmiCalculatorService(emiRepository, loanApplicationRepository);
    }

    @Test
    void testCalculateEmi() {
        double emi = emiCalculatorService.calculateEmi(100000, 12, 12);
        assertTrue(emi > 0);
    }

    @Test
    void testGenerateRepaymentSchedule_LoanNotFound() {
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LoanNotFoundException.class, () -> emiCalculatorService.generateRepaymentSchedule(1L, 10.0));
    }

    @Test
    void testGenerateRepaymentSchedule_ExistingSchedule() {
        Long loanId = 2L;
        repayment_schedule entity = repayment_schedule.builder()
                .id(10L)
                .month(1)
                .principalAmount(1000.0)
                .interestAmount(100.0)
                .balanceRemaining(9000.0)
                .build();
        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(mock(LoanApplication.class)));
        when(emiRepository.findByLoan_Id(loanId)).thenReturn(List.of(entity));

        List<RepaymentScheduleResponseDto> result = emiCalculatorService.generateRepaymentSchedule(loanId, 10.0);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals(1, result.get(0).getMonth());
        assertEquals(1000.0, result.get(0).getPrincipalAmount());
        assertEquals(100.0, result.get(0).getInterestAmount());
        assertEquals(1100.0, result.get(0).getTotalAmount());
        assertEquals(9000.0, result.get(0).getOutstandingBalance());
    }

    @Test
    void testGenerateRepaymentSchedule_NewSchedule() {
        Long loanId = 3L;
        LoanApplication loan = mock(LoanApplication.class);
        when(loan.getAmount()).thenReturn(12000.0);
        when(loan.getTenureMonths()).thenReturn(2);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(emiRepository.findByLoan_Id(loanId)).thenReturn(Collections.emptyList());

        // Mock save to return the entity with an id
        when(emiRepository.save(any())).thenAnswer(invocation -> {
            repayment_schedule arg = invocation.getArgument(0);
            return repayment_schedule.builder()
                    .id(new Random().nextLong())
                    .loan(arg.getLoan())
                    .month(arg.getMonth())
                    .principalAmount(arg.getPrincipalAmount())
                    .interestAmount(arg.getInterestAmount())
                    .balanceRemaining(arg.getBalanceRemaining())
                    .paymentUpdatedAt(arg.getPaymentUpdatedAt())
                    .build();
        });

        List<RepaymentScheduleResponseDto> result = emiCalculatorService.generateRepaymentSchedule(loanId, 12.0);

        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertEquals(1, result.get(0).getMonth());
        assertEquals(2, result.get(1).getMonth());
    }
}
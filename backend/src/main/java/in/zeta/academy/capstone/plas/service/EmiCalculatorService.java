package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.RepaymentScheduleResponseDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.repayment_schedule;
import in.zeta.academy.capstone.plas.exception.LoanNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.EmiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmiCalculatorService {

    private final EmiRepository emiRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    public double calculateEmi(double principal, double annualRate, int tenureMonths) {
        double monthlyRate = annualRate / (12 * 100); // convert annual % to monthly fraction
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    public List<RepaymentScheduleResponseDto> generateRepaymentSchedule(Long loanId, double annualRate) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        // get existing schedule if it exists
        if (!emiRepository.findByLoan_Id(loanId).isEmpty()) {
            return emiRepository.findByLoan_Id(loanId).stream()
                    .map(entity -> new RepaymentScheduleResponseDto(
                            entity.getId(),
                            entity.getMonth(),
                            entity.getPrincipalAmount(),
                            entity.getInterestAmount(),
                            entity.getPrincipalAmount() + entity.getInterestAmount(),
                            entity.getBalanceRemaining()
                    ))
                    .toList();
        }

        // if no existing schedule, creating new schedule
        List<RepaymentScheduleResponseDto> schedule = new ArrayList<>();
        double monthlyRate = annualRate / (12 * 100);
        double emi = calculateEmi(loan.getAmount(), annualRate, loan.getTenureMonths());
        double balance = loan.getAmount();

        for (int month = 1; month <= loan.getTenureMonths(); month++) {
            double interest = balance * monthlyRate;
            double principal = emi - interest;
            balance -= principal;

            repayment_schedule payment = repayment_schedule.builder()
                    .loan(loan)
                    .month(month)
                    .principalAmount(principal)
                    .interestAmount(interest)
                    .balanceRemaining(balance)
                    .paymentUpdatedAt(LocalDateTime.now())
                    .build();
            repayment_schedule savedPayment = emiRepository.save(payment);
            schedule.add(new RepaymentScheduleResponseDto(
                    savedPayment.getId(),
                    month,
                    principal,
                    interest,
                    emi,
                    balance
            ));
        }
        return schedule;
    }
}

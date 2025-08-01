package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.Emi;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.EmiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Emi> generateRepaymentSchedule(Long loanId, double annualRate) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        List<Emi> schedule = new ArrayList<>();
        double monthlyRate = annualRate / (12 * 100);
        double emi = calculateEmi(loan.getAmount(), annualRate, loan.getTenureMonths());
        double balance = loan.getAmount();

        for (int month = 1; month <= loan.getTenureMonths(); month++) {
            double interest = balance * monthlyRate;
            double principal = emi - interest;
            balance -= principal;

            Emi entry = Emi.builder()
                    .loan(loan)
                    .month(month)
                    .principalAmount(principal)
                    .interestAmount(interest)
                    .balanceRemaining(Math.max(balance, 0))
                    .build();

            schedule.add(entry);
        }
        return emiRepository.saveAll(schedule);
    }
}

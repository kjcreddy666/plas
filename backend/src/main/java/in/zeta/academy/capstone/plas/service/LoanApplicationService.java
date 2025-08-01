package in.zeta.academy.capstone.plas.service;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    @Autowired
    LoanApplicationRepository loanApplicationRepository;

    @Transactional
    public LoanApplication applyForLoan(LoanApplication loanApplication) {
        if (loanApplication.getIncome() < 25000) {
            throw new IllegalArgumentException("Income must be greater than ₹25,000");
        }
        if (loanApplication.getCreditScore() == null || loanApplication.getCreditScore() < 300 || loanApplication.getCreditScore() > 900) {
            throw new IllegalArgumentException("Credit score must be between 300 and 900");
        }

        LocalDate today = LocalDate.now();
        List<LoanApplication> recent = loanApplicationRepository
                .findByUserIdAndApplicationDate(loanApplication.getUser().getId(), today);
        if (!recent.isEmpty()) {
            throw new IllegalArgumentException("You can only submit one application every 24 hours");
        }

        loanApplication.setApplicationDate(today);
        loanApplication.setStatus(LoanApplicationStatus.NEW);
        return loanApplicationRepository.save(loanApplication);
    }

    public List<LoanApplication> getApplicationsByUser(UUID userId) {
        return loanApplicationRepository.findByUserId(userId);
    }

    public Optional<LoanApplication> getApplicationById(Long loanId) {
        return loanApplicationRepository.findById(loanId);
    }


    public List<LoanApplication> getPendingLoanApplications() {
        return loanApplicationRepository.findByStatusIn(
                List.of(LoanApplicationStatus.NEW.name(), LoanApplicationStatus.UNDER_REVIEW.name()));
    }

    public List<LoanApplication> getFilteredLoanApplications(List<LoanApplicationStatus> statuses) {
        return loanApplicationRepository.findByStatusIn(
                statuses.stream().map(LoanApplicationStatus::name).toList());
    }

    public LoanApplication updateLoanStatus(Long loanId, LoanApplicationStatus status, String remarks) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        loanApplication.setStatus(status);
        loanApplication.setReviewRemarks(remarks);

        return loanApplicationRepository.save(loanApplication);
    }
}
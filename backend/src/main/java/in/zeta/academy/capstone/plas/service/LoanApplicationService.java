package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;

import java.util.List;

public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

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

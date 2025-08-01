package in.zeta.academy.capstone.plas.service;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
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

        Users user = userRepository.findById(loanApplication.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + loanApplication.getUser().getId()));
        loanApplication.setUser(user);
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
}



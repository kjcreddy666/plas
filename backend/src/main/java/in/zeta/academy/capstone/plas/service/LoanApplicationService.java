package in.zeta.academy.capstone.plas.service;
import in.zeta.academy.capstone.plas.dto.LoanRequestDto;
import in.zeta.academy.capstone.plas.dto.LoanResponseDto;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.exception.LoanNotFoundException;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.LoanApplicationRepository;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;

    @Transactional
    public LoanResponseDto applyForLoan(LoanRequestDto dto) {
        if (dto.getIncome() < 25000) {
            throw new IllegalArgumentException("Income must be greater than ₹25,000");
        }

        if (dto.getCreditScore() < 300 || dto.getCreditScore() > 900) {
            throw new IllegalArgumentException("Credit score must be between 300 and 900");
        }

        UUID userId = UUID.fromString(dto.getUserId());
        LocalDate today = LocalDate.now();

        boolean alreadyAppliedToday = !loanApplicationRepository
                .findByUserIdAndApplicationDate(userId, today).isEmpty();
        if (alreadyAppliedToday) {
            throw new IllegalArgumentException("You can only submit one application every 24 hours");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LoanApplication application = LoanApplication.builder()
                .user(user)
                .amount(dto.getAmount())
                .tenureMonths(dto.getTenureMonths())
                .income(dto.getIncome())
                .creditScore(dto.getCreditScore())
                .status(LoanApplicationStatus.NEW)
                .applicationDate(today)
                .purpose(dto.getPurpose())
                .build();

        LoanApplication saved = loanApplicationRepository.save(application);

        return mapToDto(saved);
    }

    public List<LoanResponseDto> getApplicationsByUser(UUID userId) {
        return loanApplicationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public Optional<LoanResponseDto> getApplicationById(Long loanId) {
        return loanApplicationRepository.findById(loanId).map(this::mapToDto);
    }

    private LoanResponseDto mapToDto(LoanApplication loan) {
        return LoanResponseDto.builder()
                .id(loan.getId())
                .amount(loan.getAmount())
                .tenureMonths(loan.getTenureMonths())
                .income(loan.getIncome())
                .creditScore(loan.getCreditScore())
                .status(loan.getStatus().toString())
                .applicationDate(loan.getApplicationDate())
                .purpose(loan.getPurpose())
                .reviewedBy("ADMIN")
                .reviewedAt(loan.getReviewedAt())
                .reviewRemarks(loan.getReviewRemarks())
                .build();
    }
}

/*
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
    public LoanApplication getApplicationById(Long loanId) {
        return loanApplicationRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan application not found with ID: " + loanId));
    }
}*/



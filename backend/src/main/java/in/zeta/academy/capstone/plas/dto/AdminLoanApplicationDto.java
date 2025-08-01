package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoanApplicationDto {
    private Long loanId;
    private UUID userId;
    private Double amount;
    private Integer tenureMonths;
    private Double income;
    private Integer creditScore;
    private LoanApplicationStatus status;
    private LocalDate applicationDate;
    private String purpose;
    private String remarks;
}
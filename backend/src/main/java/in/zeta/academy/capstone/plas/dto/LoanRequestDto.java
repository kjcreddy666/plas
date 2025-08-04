package in.zeta.academy.capstone.plas.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {
    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be positive")
    private Double amount;

    @NotNull(message = "Tenure is required")
    @Min(value = 6, message = "Tenure must be at least 6 months")
    private Integer tenureMonths;

    @NotNull(message = "Income is required")
    @Min(value = 25000, message = "Income must be at least ₹25,000")
    private Double income;

    @NotNull(message = "Credit score is required")
    @Min(value = 300, message = "Credit score must be at least 300")
    @Max(value = 900, message = "Credit score must not exceed 900")
    private Integer creditScore;

    @Size(max = 1000, message = "Purpose must not exceed 1000 characters")
    private String purpose;
}

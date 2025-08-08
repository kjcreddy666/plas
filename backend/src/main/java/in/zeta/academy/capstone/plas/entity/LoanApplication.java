package in.zeta.academy.capstone.plas.entity;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loan_applications")
// This class represents a loan application entity in the system
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private Users user;
    @NotNull(message = "Loan amount is required")
    private Double amount;
    @NotNull(message = "Tenure (in months) is required")
    @Min(value = 6, message = "Tenure must be at least 6 months")
    private Integer tenureMonths;
    @NotNull(message = "Income is required")
    private Double income;
    @NotNull(message = "Credit score is required")
    @Min(value = 300, message = "Credit score must be at least 300")
    @Max(value = 900, message = "Credit score cannot exceed 900")
    private Integer creditScore;
    @Enumerated(EnumType.STRING)
    private LoanApplicationStatus status;
    private LocalDate applicationDate;
    @Size(max = 10000, message = "Purpose must not exceed 1000 characters")
    private String purpose;
    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private Users reviewedBy;
    private LocalDateTime reviewedAt;
    @Size(max = 1000, message = "Review remarks must not exceed 1000 characters")
    private String reviewRemarks;

}

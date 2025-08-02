package in.zeta.academy.capstone.plas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "repayment_schedule")
public class repayment_schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Loan reference is required")
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loan;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be at least 1")
    private Integer month;

    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Principal amount must be greater than 0")
    private Double principalAmount;

    @NotNull(message = "Interest amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Interest amount cannot be negative")
    private Double interestAmount;

    @NotNull(message = "Balance remaining is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance remaining cannot be negative")
    private Double balanceRemaining;

    @PastOrPresent(message = "Payment updated date cannot be in the future")
    private LocalDateTime paymentUpdatedAt;
}

package in.zeta.academy.capstone.plas.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {
    private Long id;
    private Double amount;
    private Integer tenureMonths;
    private Double income;
    private Integer creditScore;
    private String status;
    private LocalDate applicationDate;
    private String purpose;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewRemarks;
}

package in.zeta.academy.capstone.plas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentScheduleResponseDto {
    private Long id;
    private Integer month;
    private Double principalAmount;
    private Double interestAmount;
    private Double totalAmount;
    private Double outstandingBalance;
}

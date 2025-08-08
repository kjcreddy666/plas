package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// to update the status of a loan application.
public class LoanUpdationRequestDto {
    private LoanApplicationStatus status;
    private String remarks;
}
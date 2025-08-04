package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {
    private Long Id;
    private Long loanId;
    private String subject;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String response;
}

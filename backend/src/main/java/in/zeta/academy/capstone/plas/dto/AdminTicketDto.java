package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.TicketStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminTicketDto {
    private Long id;
    private UUID userId;
    private Long loanId;
    private String subject;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String response;
}

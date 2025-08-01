package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.entity.LoanApplication;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class TicketRequestDto {

    @NotNull
    private UUID userId;

    @NotNull
    private Long loanApplicationId;

    @NotBlank(message = "Subject is required")
    @Size(max = 100, message = "Subject can be at most 100 characters")
    private String subject;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description can be at most 1000 characters")
    private String description;

}

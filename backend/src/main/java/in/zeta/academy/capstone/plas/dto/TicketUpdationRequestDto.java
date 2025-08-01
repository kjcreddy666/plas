package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdationRequestDto {

    @NotNull
    private String response;
}
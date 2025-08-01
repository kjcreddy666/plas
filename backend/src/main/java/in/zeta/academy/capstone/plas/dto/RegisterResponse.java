package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    @NotBlank
    private String isSuccess;

    @NotBlank
    private String message;
}

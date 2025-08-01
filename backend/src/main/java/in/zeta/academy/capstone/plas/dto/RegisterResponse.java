package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponse {
    @NotBlank
    private String isSuccess;

    @NotBlank
    private String message;
}

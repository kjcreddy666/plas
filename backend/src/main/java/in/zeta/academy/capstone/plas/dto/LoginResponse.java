package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class LoginResponse {
    @NotBlank
    private boolean isSuccess;
    @NotBlank
    private String message;
    private String token;
    private UUID id;
}

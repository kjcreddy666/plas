package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @NotBlank
    private boolean isSuccess;
    @NotBlank
    private String message;
    private String token;
    private UUID id;
}

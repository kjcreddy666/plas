package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    @NotBlank
    private boolean isSuccess;
    @NotBlank
    private String message;
    private String token;
    private UUID id;
    private Role role;
}

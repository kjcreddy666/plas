package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    @NotBlank
    private String isSuccess;

    @NotBlank
    private String message;
}

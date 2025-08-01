package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto{
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be 10 digits starting with 6, 7, 8, or 9")
    private String mobile;

    @NotBlank
    private String password;
}

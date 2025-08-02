package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.validation.EitherEmailOrMobileRequired;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EitherEmailOrMobileRequired
public class LoginRequestDto{
    @Email
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be 10 digits starting with 6, 7, 8, or 9")
    private String mobile;

    @NotBlank
    private String password;
}

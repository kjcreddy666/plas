package in.zeta.academy.capstone.plas.validation;

import in.zeta.academy.capstone.plas.dto.LoginRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EitherEmailOrMobileValidator implements ConstraintValidator<EitherEmailOrMobileRequired, LoginRequestDto> {

    @Override
    public boolean isValid(LoginRequestDto dto, ConstraintValidatorContext context) {
        return (dto.getEmail() != null && !dto.getEmail().isBlank()) ||
                (dto.getMobile() != null && !dto.getMobile().isBlank());
    }
}
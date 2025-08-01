package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.LoginRequestDto;
import in.zeta.academy.capstone.plas.dto.LoginResponseDto;
import in.zeta.academy.capstone.plas.dto.RegisterRequestDto;
import in.zeta.academy.capstone.plas.dto.RegisterResponseDto;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.Role;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;

    public LoginResponseDto login(@Valid @NotNull LoginRequestDto loginRequestDto) {
        Users user;

        if (loginRequestDto.getEmail() != null && !loginRequestDto.getEmail().isEmpty()) {
            user = userService.getUserByEmail(loginRequestDto.getEmail());
        } else if (loginRequestDto.getMobile() != null && !loginRequestDto.getMobile().isEmpty()) {
            user = userService.getUserByMobile(Long.parseLong(loginRequestDto.getMobile()));
        } else {
            throw new UserNotFoundException("Either email or mobile must be provided for login.");
        }

        boolean passwordMatches = user.getPassword().equals(loginRequestDto.getPassword());

        return LoginResponseDto.builder()
                .isSuccess(passwordMatches)
                .message(passwordMatches ? "Login successful" : "Invalid credentials")
                .token(passwordMatches ? jwtService.generateToken(user) : null) // TODO: Replace with actual token
                .id(passwordMatches ? user.getId() : null)
                .build();
    }

    public RegisterResponseDto register(@Valid @NotNull RegisterRequestDto registerRequestDto) {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name(registerRequestDto.getName())
                .email(registerRequestDto.getEmail())
                .mobile(Long.parseLong(registerRequestDto.getMobile()))
                .password(registerRequestDto.getPassword())
                .address(registerRequestDto.getAddress())
                .role(Role.CUSTOMER) // Default role
                .build();

        userService.createUser(user);

        return RegisterResponseDto.builder()
                .isSuccess("true")
                .message("User registered successfully")
                .build();
    }
}

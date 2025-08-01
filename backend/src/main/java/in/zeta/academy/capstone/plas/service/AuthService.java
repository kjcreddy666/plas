package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.LoginRequest;
import in.zeta.academy.capstone.plas.dto.LoginResponse;
import in.zeta.academy.capstone.plas.dto.RegisterRequest;
import in.zeta.academy.capstone.plas.dto.RegisterResponse;
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

    public LoginResponse login(@Valid @NotNull LoginRequest loginRequest) {
        Users user;

        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty()) {
            user = userService.getUserByEmail(loginRequest.getEmail());
        } else if (loginRequest.getMobile() != null && !loginRequest.getMobile().isEmpty()) {
            user = userService.getUserByMobile(Long.parseLong(loginRequest.getMobile()));
        } else {
            throw new UserNotFoundException("Either email or mobile must be provided for login.");
        }

        boolean passwordMatches = user.getPassword().equals(loginRequest.getPassword());

        return LoginResponse.builder()
                .isSuccess(passwordMatches)
                .message(passwordMatches ? "Login successful" : "Invalid credentials")
                .token(passwordMatches ? jwtService.generateToken(user) : null) // TODO: Replace with actual token
                .id(passwordMatches ? user.getId() : null)
                .build();
    }

    public RegisterResponse register(@Valid @NotNull RegisterRequest registerRequest) {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .mobile(Long.parseLong(registerRequest.getMobile()))
                .password(registerRequest.getPassword())
                .address(registerRequest.getAddress())
                .role(Role.CUSTOMER) // Default role
                .build();

        userService.createUser(user);

        return RegisterResponse.builder()
                .isSuccess("true")
                .message("User registered successfully")
                .build();
    }
}

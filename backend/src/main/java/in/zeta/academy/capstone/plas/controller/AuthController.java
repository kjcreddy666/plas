package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.LoginRequest;
import in.zeta.academy.capstone.plas.dto.LoginResponse;
import in.zeta.academy.capstone.plas.dto.RegisterRequest;
import in.zeta.academy.capstone.plas.dto.RegisterResponse;
import in.zeta.academy.capstone.plas.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}

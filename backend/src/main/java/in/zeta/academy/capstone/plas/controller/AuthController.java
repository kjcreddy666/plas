package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.LoginRequestDto;
import in.zeta.academy.capstone.plas.dto.LoginResponseDto;
import in.zeta.academy.capstone.plas.dto.RegisterRequestDto;
import in.zeta.academy.capstone.plas.dto.RegisterResponseDto;
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
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto response = authService.register(registerRequestDto);
        return ResponseEntity.ok(response);
    }
}

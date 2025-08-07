package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.LoginRequestDto;
import in.zeta.academy.capstone.plas.dto.LoginResponseDto;
import in.zeta.academy.capstone.plas.dto.RegisterRequestDto;
import in.zeta.academy.capstone.plas.dto.RegisterResponseDto;
import in.zeta.academy.capstone.plas.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequestDto request = new LoginRequestDto();
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setToken("token");
//        responseDto.setError(null);

        when(authService.login(request)).thenReturn(responseDto);

        ResponseEntity<LoginResponseDto> response = authController.login(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testLogin_Failure() {
        LoginRequestDto request = new LoginRequestDto();
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setSuccess(false);
        responseDto.setToken(null);
//        responseDto.setError("Invalid credentials");

        when(authService.login(request)).thenReturn(responseDto);

        ResponseEntity<LoginResponseDto> response = authController.login(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testRegister() {
        RegisterRequestDto request = new RegisterRequestDto();
        RegisterResponseDto responseDto = new RegisterResponseDto("true", "Registered");
        when(authService.register(request)).thenReturn(responseDto);

        ResponseEntity<RegisterResponseDto> response = authController.register(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }
}
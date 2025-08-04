package in.zeta.academy.capstone.plas.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.zeta.academy.capstone.plas.dto.LoanRequestDto;
import in.zeta.academy.capstone.plas.dto.LoanResponseDto;
import in.zeta.academy.capstone.plas.service.LoanApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanApplicationService loanApplicationService;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(loanApplicationController).build();
    }

    @Test
    void applyForLoan_shouldReturnSavedApplication() throws Exception {
        LoanRequestDto requestDto = new LoanRequestDto();
        requestDto.setTenureMonths(12);
        requestDto.setAmount(50000.0);
        requestDto.setCreditScore(750);
        requestDto.setIncome(100000.0);
        requestDto.setUserId("test-user-id");

        LoanResponseDto responseDto = new LoanResponseDto();
        Mockito.when(loanApplicationService.applyForLoan(any(LoanRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/loans/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void getApplicationsByUser_shouldReturnListOfApplications() throws Exception {
        UUID userId = UUID.randomUUID();
        List<LoanResponseDto> responseList = List.of(new LoanResponseDto());
        Mockito.when(loanApplicationService.getApplicationsByUser(eq(userId))).thenReturn(responseList);

        mockMvc.perform(get("/loans/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseList)));
    }

    @Test
    void getApplicationById_shouldReturnApplicationIfFound() throws Exception {
        long loanId = 1L;
        LoanResponseDto responseDto = new LoanResponseDto();
        Mockito.when(loanApplicationService.getApplicationById(eq(loanId))).thenReturn(Optional.of(responseDto));

        mockMvc.perform(get("/loans/{loanId}", loanId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void getApplicationById_shouldReturnNotFoundIfMissing() throws Exception {
        long loanId = 2L;
        Mockito.when(loanApplicationService.getApplicationById(eq(loanId))).thenReturn(Optional.empty());

        mockMvc.perform(get("/loans/{loanId}", loanId))
                .andExpect(status().isNotFound());
    }
}
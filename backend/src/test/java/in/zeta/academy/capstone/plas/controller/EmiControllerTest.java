package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.EmiRequestDto;
import in.zeta.academy.capstone.plas.dto.RepaymentScheduleResponseDto;
import in.zeta.academy.capstone.plas.service.EmiCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmiControllerTest {

    private EmiCalculatorService emiCalculatorService;
    private EmiController emiController;

    @BeforeEach
    void setUp() {
        emiCalculatorService = mock(EmiCalculatorService.class);
        emiController = new EmiController(emiCalculatorService);
    }

    @Test
    void testPreviewEmi() {
        EmiRequestDto request = new EmiRequestDto();
        request.setAmount(100000);
        request.setAnnualRate(10.0);
        request.setTenureMonths(12);

        when(emiCalculatorService.calculateEmi(100000.0, 10.0, 12)).thenReturn(8791.59);

        ResponseEntity<Map<String, Object>> response = emiController.previewEmi(request);

        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(100000.0, body.get("amount"));
        assertEquals(10.0, body.get("annualRate"));
        assertEquals(12, body.get("tenureMonths"));
        assertEquals(8791.59, body.get("emi"));

        verify(emiCalculatorService, times(1)).calculateEmi(100000, 10.0, 12);
    }

    @Test
    void testGenerateSchedule() {
        Long loanId = 1L;
        double annualRate = 10.0;
        List<RepaymentScheduleResponseDto> mockSchedule = List.of(
                new RepaymentScheduleResponseDto(1L, 1, 1000.0, 100.0, 1100.0, 90000.0)
        );

        when(emiCalculatorService.generateRepaymentSchedule(loanId, annualRate)).thenReturn(mockSchedule);

        ResponseEntity<List<RepaymentScheduleResponseDto>> response = emiController.generateSchedule(loanId, annualRate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockSchedule, response.getBody());

        verify(emiCalculatorService, times(1)).generateRepaymentSchedule(loanId, annualRate);
    }
}
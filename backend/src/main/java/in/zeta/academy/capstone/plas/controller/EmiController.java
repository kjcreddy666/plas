package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.EmiRequestDto;
import in.zeta.academy.capstone.plas.dto.RepaymentScheduleResponseDto;
import in.zeta.academy.capstone.plas.service.EmiCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emi")
@RequiredArgsConstructor
public class EmiController {

    private final EmiCalculatorService emiCalculatorService;

    @PostMapping("/preview")
    public ResponseEntity<Map<String, Object>> previewEmi(@RequestBody EmiRequestDto request) {
        double emi = emiCalculatorService.calculateEmi(request.getAmount(), request.getAnnualRate(), request.getTenureMonths());
        return ResponseEntity.ok(Map.of(
                "amount", request.getAmount(),
                "annualRate", request.getAnnualRate(),
                "tenureMonths", request.getTenureMonths(),
                "emi", emi
        ));
    }

    @PostMapping("/repayments/{loanId}")
    public ResponseEntity<List<RepaymentScheduleResponseDto>> generateSchedule(@PathVariable Long loanId,
                                                                     @RequestParam double annualRate) {
        List<RepaymentScheduleResponseDto> schedule = emiCalculatorService.generateRepaymentSchedule(loanId, annualRate);
        return ResponseEntity.ok(schedule);
    }
}


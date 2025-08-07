package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.EmiRequestDto;
import in.zeta.academy.capstone.plas.dto.PageResponse;
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
    public ResponseEntity<PageResponse<RepaymentScheduleResponseDto>> generateSchedule(
            @PathVariable Long loanId,
            @RequestParam double annualRate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        List<RepaymentScheduleResponseDto> schedule = emiCalculatorService.generateRepaymentSchedule(loanId, annualRate);
        int start = Math.min(page * size, schedule.size());
        int end = Math.min(start + size, schedule.size());
        List<RepaymentScheduleResponseDto> pagedList = schedule.subList(start, end);

        PageResponse<RepaymentScheduleResponseDto> response = new PageResponse<>(
                pagedList, page, size, schedule.size()
        );
        return ResponseEntity.ok(response);
    }
}


package in.zeta.academy.capstone.plas.controller;
import in.zeta.academy.capstone.plas.dto.LoanRequestDto;
import in.zeta.academy.capstone.plas.dto.LoanResponseDto;
import in.zeta.academy.capstone.plas.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<LoanResponseDto> applyForLoan(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        // Validate the loan request DTO
        LoanResponseDto savedApp = loanApplicationService.applyForLoan(loanRequestDto);
        return ResponseEntity.ok(savedApp);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponseDto>> getApplicationsByUser(@PathVariable UUID userId) {
        // Fetch all loan applications for a specific user
        return ResponseEntity.ok(loanApplicationService.getApplicationsByUser(userId));
    }
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponseDto> getApplicationById(@PathVariable Long loanId) {
        // Fetch a specific loan application by its ID
        return loanApplicationService.getApplicationById(loanId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

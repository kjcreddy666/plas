package in.zeta.academy.capstone.plas.controller;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanApplicationController {
    @Autowired
    private final LoanApplicationService loanApplicationService;
    @PostMapping("/apply")
    public ResponseEntity<LoanApplication> applyForLoan(@Valid @RequestBody LoanApplication loanApplication) {
        LoanApplication savedApp = loanApplicationService.applyForLoan(loanApplication);
        return ResponseEntity.ok(savedApp);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanApplication>> getApplicationsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(loanApplicationService.getApplicationsByUser(userId));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long loanId) {
        return loanApplicationService.getApplicationById(loanId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


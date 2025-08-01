package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.entity.LoanApplication;
import in.zeta.academy.capstone.plas.enums.LoanApplicationStatus;
import in.zeta.academy.capstone.plas.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/loans")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    LoanApplicationService loanApplicationService;

    @GetMapping("/pending")
    public ResponseEntity<List<LoanApplication>> getPendingApplications(){
        List<LoanApplication> pendingApplications = loanApplicationService.getPendingLoanApplications();
        if (pendingApplications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pendingApplications);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LoanApplication>> getFilteredApplications(@RequestParam List<LoanApplicationStatus> statuses) {
        List<LoanApplication> filteredApplications = loanApplicationService.getFilteredLoanApplications(statuses);
        if (filteredApplications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filteredApplications);
    }

    @PutMapping("/{loanId}/status")
    public ResponseEntity<LoanApplication> updateLoanStatus(@PathVariable Long loanId,
                                                            @RequestParam LoanApplicationStatus status,
                                                            @RequestParam(required = false) String remarks) {
        LoanApplication updated = loanApplicationService.updateLoanStatus(loanId, status, remarks);
        return ResponseEntity.ok(updated);
    }

}

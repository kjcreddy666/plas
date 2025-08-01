package in.zeta.academy.capstone.plas.repository;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
     List<LoanApplication> findByUserIdAndApplicationDate(UUID id, LocalDate today) ;
    List<LoanApplication> findByUserId(Long userId);
    List<LoanApplication> findByStatusIn(List<String> statuses);
}

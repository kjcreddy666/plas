package in.zeta.academy.capstone.plas.repository;
import in.zeta.academy.capstone.plas.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    @Query("SELECT la FROM LoanApplication la WHERE la.user.id = ?1 AND la.applicationDate = ?2")
    List<LoanApplication> findByUserIdAndApplicationDate(UUID id, LocalDate today) ;

    @Query("SELECT la FROM LoanApplication la WHERE la.user.id = ?1")
    List<LoanApplication> findByUserId(UUID userId);

    List<LoanApplication> findByStatusIn(List<String> statuses);
}

package in.zeta.academy.capstone.plas.repository;


import in.zeta.academy.capstone.plas.entity.repayment_schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmiRepository extends JpaRepository<repayment_schedule, Long> {

    List<repayment_schedule> findByLoan_Id(Long loanId);
}

package in.zeta.academy.capstone.plas.repository;


import in.zeta.academy.capstone.plas.entity.Emi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmiRepository extends JpaRepository<Emi, Long> {

    List<Emi> findByLoan_Id(Long loanId);

    Emi findByLoan_IdAndMonth(Long loanId, Integer month);
}

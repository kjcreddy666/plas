package in.zeta.academy.capstone.plas.repository;

import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByMobile(Long mobile);
    boolean existsByEmail(String email);
    boolean existsByMobile(Long mobile);
    Users findByRole(Role role);
}
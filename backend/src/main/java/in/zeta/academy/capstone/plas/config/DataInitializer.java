package in.zeta.academy.capstone.plas.config;

import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.Role;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            Users admin = Users.builder()
                    .id(UUID.fromString("a0000000-0000-0000-0000-000000000001"))
                    .name("Administrator")
                    .email("admin@gmail.com")
                    .mobile(9876543210L)
                    .password(passwordEncoder.encode("12@admin"))
                    .address("Myhometwitza")
                    .role(Role.ADMIN)
                    .build();
            
            userRepository.save(admin);
            System.out.println("✅ Default admin user created successfully!");
            System.out.println("📧 Email: admin@gmail.com");
            System.out.println("🔑 Password: 12@admin");
        } else {
            System.out.println("ℹ️ Admin user already exists, skipping creation.");
        }
    }
}

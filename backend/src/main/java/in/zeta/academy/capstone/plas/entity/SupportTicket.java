package in.zeta.academy.capstone.plas.entity;

import in.zeta.academy.capstone.plas.enums.TicketStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private LoanApplication loanApplication;

    @Size(max = 100, message = "Subject can be at most 100 characters")
    @NotBlank(message = "Subject is required")
    @Column( nullable = false)
    private String subject;

    @Size(max = 1000, message = "Description can be at most 1000 characters")
    @Column( nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Size(max = 1000, message = "Response can be at most 1000 characters")
    @Column(nullable = false)
    @NotBlank(message = "Response is required")
    private String response;

}

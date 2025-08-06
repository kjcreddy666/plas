package in.zeta.academy.capstone.plas.dto;

import in.zeta.academy.capstone.plas.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private Long mobile;
    private String password;
    private String address;
    private Role role;
}
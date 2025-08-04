package in.zeta.academy.capstone.plas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.zeta.academy.capstone.plas.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto {

    private UUID id;
    private String name;
    private String email;
    private Long mobile;
    private String address;
    @JsonIgnore
    private Role role;

}

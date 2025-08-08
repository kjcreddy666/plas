package in.zeta.academy.capstone.plas.controller;

import in.zeta.academy.capstone.plas.dto.UserDto;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody Users user) {
        Users createdUser = userService.createUser(user);
        return ResponseEntity.ok(toDto(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(toDto(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        Users user = userService.getUserByEmail(email);
        return ResponseEntity.ok(toDto(user));
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<UserDto> getUserByMobile(@PathVariable Long mobile) {
        Users user = userService.getUserByMobile(mobile);
        return ResponseEntity.ok(toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody Users updatedUser) {
        Users user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Mapper method
    private UserDto toDto(Users user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .address(user.getAddress())
                .role(user.getRole())
                .build(); // Password is excluded
    }
}

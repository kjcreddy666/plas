package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.dto.UserDto;
import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.exception.UserAlreadyExistException;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("Email already exists: " + user.getEmail());
        }
        if (userRepository.existsByMobile(user.getMobile())) {
            throw new UserAlreadyExistException("Mobile number already exists: " + user.getMobile());
        }

        user.setId(UUID.randomUUID());
        return entityToDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return entityToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return entityToDto(user);
    }

    public UserDto getUserByMobile(Long mobile) {
        Users user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new UserNotFoundException("User not found with mobile: " + mobile));
        return entityToDto(user);
    }

    public UserDto updateUser(UUID id, UserDto updatedDto) {
        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        existingUser.setName(updatedDto.getName());
        existingUser.setEmail(updatedDto.getEmail());
        existingUser.setMobile(updatedDto.getMobile());
        existingUser.setAddress(updatedDto.getAddress());
        existingUser.setRole(updatedDto.getRole());

        return entityToDto(userRepository.save(existingUser));
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // === Mapper ===
    private UserDto entityToDto(Users user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}

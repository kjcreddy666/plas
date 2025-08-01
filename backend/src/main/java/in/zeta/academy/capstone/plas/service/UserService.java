package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.exception.UserAlreadyExistException;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Users createUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("Email already exists: " + user.getEmail());
        }
        if (userRepository.existsByMobile(user.getMobile())) {
            throw new UserAlreadyExistException("Mobile number already exists: " + user.getMobile());
        }

        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public Users getUserByMobile(Long mobile) {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new UserNotFoundException("User not found with mobile: " + mobile));
    }

    public Users updateUser(UUID id, Users updatedUser) {
        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMobile(updatedUser.getMobile());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

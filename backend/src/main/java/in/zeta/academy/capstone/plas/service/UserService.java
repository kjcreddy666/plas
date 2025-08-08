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
        if(updatedUser.getEmail() == null || updatedUser.getEmail().isEmpty())
        {
            existingUser.setEmail(existingUser.getEmail());
        } else if(userRepository.existsByEmail(updatedUser.getEmail()) && !existingUser.getEmail().equals(updatedUser.getEmail())) {
            throw new UserAlreadyExistException("Email already exists: " + updatedUser.getEmail());
        }else{
            existingUser.setEmail(updatedUser.getEmail());
        }

        if(updatedUser.getMobile() == null || updatedUser.getMobile() == 0)
        {
            existingUser.setMobile(existingUser.getMobile());
        }else if(userRepository.existsByMobile(updatedUser.getMobile()) && !existingUser.getMobile().equals(updatedUser.getMobile())) {
            throw new UserAlreadyExistException("Mobile number already exists: " + updatedUser.getMobile());
        }else{
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty())
        {
            existingUser.setPassword(existingUser.getPassword());
        }else {
            existingUser.setPassword(updatedUser.getPassword());
        }
        existingUser.setAddress(updatedUser.getAddress());

        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

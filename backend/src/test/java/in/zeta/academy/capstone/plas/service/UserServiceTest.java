package in.zeta.academy.capstone.plas.service;

import in.zeta.academy.capstone.plas.entity.Users;
import in.zeta.academy.capstone.plas.enums.Role;
import in.zeta.academy.capstone.plas.exception.UserAlreadyExistException;
import in.zeta.academy.capstone.plas.exception.UserNotFoundException;
import in.zeta.academy.capstone.plas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = Users.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@example.com")
                .mobile(1234567890L)
                .password("password")
                .address("Test Address")
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByMobile(user.getMobile())).thenReturn(false);
        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users created = userService.createUser(user);
        assertNotNull(created);
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void createUser_emailExists_throwsException() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
    }

    @Test
    void getUserById_found() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Users found = userService.getUserById(user.getId());
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(UUID.randomUUID()));
    }

    @Test
    void updateUser_success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users updated = userService.updateUser(user.getId(), user);
        assertNotNull(updated);
    }

    @Test
    void deleteUser_success() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(user.getId());
        assertDoesNotThrow(() -> userService.deleteUser(user.getId()));
    }

    @Test
    void deleteUser_notFound() {
        when(userRepository.existsById(any())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(UUID.randomUUID()));
    }
}
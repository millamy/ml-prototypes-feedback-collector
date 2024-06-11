package protopnet.mlprototypesfeedbackcollector.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import protopnet.mlprototypesfeedbackcollector.model.User;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserService.
 */
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for successful user registration.
     */
    @Test
    public void testRegisterUserSuccess() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Test for user registration when the username is already taken.
     */
    @Test
    public void testRegisterUserUsernameTaken() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        assertThrows(RuntimeException.class, () -> userService.registerUser(user));
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(0)).save(user);
    }

    /**
     * Test for finding a user by username.
     */
    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}

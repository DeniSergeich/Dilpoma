package ru.sergeich.diploma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositories.UserRepository;
import ru.sergeich.diploma.services.impl.UserServiceImpl;


import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserRepository userRepository;
    private User testUser;
    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("test");
        Cart cart = new Cart(testUser);
        testUser.setCart(cart);
    }

    @Test
    public void registerUser() {


        when(userRepository.save(testUser)).thenReturn(testUser);

        userServiceImpl.saveUser(testUser);

        verify(userRepository, times(1)).save(testUser);
    }
    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.loadUserByUsername("non-existent-user");
        });
    }
    @Test
    public void testRegisterUser_Success() {


        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(null);
        when(userRepository.save(testUser)).thenReturn(testUser);
        userServiceImpl.saveUser(testUser);
        verify(userRepository, times(1)).save(testUser);
    }
}

package ru.sergeich.diploma.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sergeich.diploma.domain.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    Optional<User> getUserById(Long userId);
    User findUserByUsername(String username);
    void saveUser(User user);
    void rootResaveUserWithPassword(User user);
    void registerUser(User user);
    User getCurrentUser();
}

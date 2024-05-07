package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.exceptions.UserNotFoundException;

/**
 * Сервис для обновления авторизованного пользователя в контексте безопасности
 */
@Service
public class AuthService {
    @Autowired
    UserService userService;

    /**
     * Обновляет пользователя в контексте безопасности
     * @param user пользователь с обновленными данными
     */
    public void updateAuthentication(User user) {
            // Создаем новый объект User с обновленными данными
            User updatedUser = userService.getUserById(user.getId()).orElseThrow(UserNotFoundException::new);
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(updatedUser, null, updatedUser.getAuthorities());
            // Устанавливаем новый объект Authentication в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }



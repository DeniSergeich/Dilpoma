package ru.sergeich.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositories.UserRepository;
import ru.sergeich.diploma.services.UserService;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    /**
     * Извлекает объект UserDetails по имени пользователя из репозитория пользователей.
     *
     * @param username — имя пользователя, которого требуется получить.
     * @return объект UserDetails, представляющий пользователя
     * @throws UsernameNotFoundException, если пользователь не найден в репозитории
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    /**
     * Извлекает пользователя из хранилища пользователей по его идентификатору.
     *
     * @param userId — идентификатор пользователя, которого нужно получить.
     * @return необязательный параметр, содержащий объект User, если он найден, в противном случае пустой
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Находит пользователя в хранилище пользователей по его имени.
     *
     * @param username — имя пользователя, которого нужно найти.
     * @return объект User, представляющий найденного пользователя, или значение null, если пользователь не найден
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Сохраняет данного пользователя в хранилище пользователей.
     *
     * @param user — объект пользователя, который необходимо сохранить.
     */
    public void saveUser(User user) {
        userRepository.save(user);
        log.info("User {} saved", user.getUsername() + "с корзиной " + user.getCart().getId());
    }

    /**
     * Шифрует пароль пользователя и сохраняет обновленный объект пользователя в базе данных.
     *
     * @param user — объект пользователя с паролем, который необходимо зашифровать и сохранить.
     */
    public void rootResaveUserWithPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param user — объект пользователя, содержащий регистрационные данные.
     * @throws UsernameNotFoundException, если имя пользователя уже существует в базе данных
     */
    public void registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

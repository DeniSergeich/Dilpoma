package ru.sergeich.diploma.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositoryes.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return user;
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User findUserById(long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Transactional
    @Async
    public void sendGoods(User user, Set<Cart> carts, String totalCost) {
        String mailBody = "Спасибо за Ваш заказ !\nВаш заказ:\n";
        for (Cart item : carts) {
            mailBody += item.getBouquetName() + " | " + item.getPrice(false) + "/ед | " + item.getBouquetCount() + " шт. | " + item.getPrice(true) + "\n";
        }
        mailBody += "Итого: " + totalCost;
        mailService.sendEmail(user.getEmail(), "Заказ оформлен!", mailBody);
    }

    @Transactional
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void rootReSaveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void rootReSaveUserWithPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean deleteUser(long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Transactional
    public List<User> usergtList(int idMin) {
        return entityManager.createQuery("SELECT u from User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}

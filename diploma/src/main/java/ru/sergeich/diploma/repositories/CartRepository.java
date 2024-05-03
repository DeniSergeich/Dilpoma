package ru.sergeich.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserID(User user);
}

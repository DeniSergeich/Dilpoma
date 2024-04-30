package ru.sergeich.diploma.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserID(User user);
    Cart findCartByUserIDAndBouquetNumber(User user, int bouquetNumber);
}

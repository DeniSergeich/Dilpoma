package ru.sergeich.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(User user);

    Cart findByUser(User currentUser);


    default void deleteCartById(Long id) {
        Cart cart = findById(id).orElse(null);
        assert cart != null;
        User user = cart.getUser();
        user.setCart(null);
        cart.setUser(null);
        cart.setBouquets(null);
        delete(cart);
    }
}

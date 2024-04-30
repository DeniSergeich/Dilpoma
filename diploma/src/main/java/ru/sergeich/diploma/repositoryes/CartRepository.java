package ru.sergeich.diploma.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(long id);
    Cart findByUserIdAndBouquetNumber(long userId, int bouquetNumber);
}

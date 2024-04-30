package ru.sergeich.diploma.services;

import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.repositoryes.CartRepository;

import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public List<Cart> findCartsByUserId(long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    @Transactional
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Transactional
    public Cart findCartByUserAndBouquetNumber(long userId, int bouquetNumber) {
        return cartRepository.findByUserIdAndBouquetNumber(userId, bouquetNumber);
    }

    @Transactional
    public void deleteCart(Cart cart) {
        cartRepository.deleteById(cart.getId());
    }

    @Transactional
    public void deleteCarts(Set<Cart> cartSet) {
        cartRepository.deleteAll(cartSet);
    }

    @Transactional
    public void changeCountOfBouquet(Cart cart, boolean plus) {
        if (plus) cart.setBouquetCount(cart.getBouquetCount() + 1);
        else cart.setBouquetCount(cart.getBouquetCount() - 1);
        cartRepository.save(cart);
    }
}

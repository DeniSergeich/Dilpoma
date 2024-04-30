package ru.sergeich.diploma.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositoryes.CartRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public List<Cart> findCartByUserID(User user){
        return cartRepository.findAllByUserID(user);
    }

    @Transactional
    public void addCart(Cart cart){
        cartRepository.save(cart);
    }

    @Transactional
    public Cart findCartByUserAndBNumber(User user, int bouquet_number){
        return cartRepository.findCartByUserIDAndBouquetNumber(user, bouquet_number);
    }

    @Transactional
    public void deleteCart(Cart cart){
        cartRepository.deleteById(cart.getId());
    }

    @Transactional
    public void deleteCarts(Set<Cart> cartSet){
        cartRepository.deleteAll(cartSet);
    }

    @Transactional
    public void changeCountOfBouquet(Cart cart, boolean plus){
        if (plus){
            cart.setbouquetCount(cart.getbouquetCount() + 1);
        } else
            cart.setbouquetCount(cart.getbouquetCount() - 1);
        cartRepository.save(cart);
    }
}

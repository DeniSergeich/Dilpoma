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
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public List<Cart> findCartByUserID(User user){
        return cartRepository.findAllByUserID(user);
    }

    public void addCart(Cart cart){
        cartRepository.save(cart);
    }

    @Transactional
    public Cart findCartByUserAndBNumber(User user, int bouquet_number){
        return cartRepository.findCartByUserIDAndBouquetNumber(user, bouquet_number);
    }

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
            cart.setBouquetCount(cart.getBouquetCount() + 1);
        } else
            cart.setBouquetCount(cart.getBouquetCount() - 1);
        cartRepository.save(cart);
    }
    @Transactional
    public void workWithCart(User user, int bouquetNumber, boolean plus){
        if (user.getBCount(bouquetNumber) == 0){
            if (!plus)
                return;
            Cart cart = new Cart(user, bouquetNumber);
            cart.setBouquetCount(1);
            user.getList().add(cart);
            addCart(cart);
        }   else {
            Cart cart = user.getB(bouquetNumber);
            if (user.getBCount(bouquetNumber) == 1 & !plus){
                user.getList().remove(cart);
                userService.rootResaveUser(user);
                deleteCart(cart);
            }
            else
                changeCountOfBouquet(cart, plus);
        }
    }
}

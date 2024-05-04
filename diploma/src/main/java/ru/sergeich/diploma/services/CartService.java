package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.exceptions.*;
import ru.sergeich.diploma.repositories.CartRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BouquetService bouquetService;


    public Cart createCart(User user) {
        user.setCart(new Cart(user));
        return cartRepository.save(new Cart(user));
    }

    /**
     * Удаление корзины по айди
     * @param cartId удаляемая корзина
     */

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    /**
     * Получение корзины по айди
     * @param cartId ид корзины
     * @return корзина
     */

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);
    }

    /**
     * Обновление корзины. Добавление/удаление букета
     * @param bouquetId букет для добавления/удаления
     * @param isAdd true - добавление, false - удаление
     */

    public void updateCart(User user, Long bouquetId, boolean isAdd) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = createCart(user);
            user.setCart(cart);
            userService.saveUser(user);
        }

        if (isAdd) {
            addBouquetToCart(cart, bouquetId);
        } else {
            removeBouquetFromCart(cart, bouquetId);
        }
    }

    /**
     * Добавление букета в корзину
     * @param bouquetId букет для добавления
     */
    public void addBouquetToCart(Cart cart, Long bouquetId) {
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.add(bouquetService.getBouquetById(bouquetId)
                .orElseThrow(BouquetNotFoundException::new));
        cartRepository.save(cart);
    }

    /**
     * Удаление букета из корзины
     * @param bouquetId букет для удаления
     */
    public void removeBouquetFromCart(Cart cart, Long bouquetId) {
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.remove(bouquetService.getBouquetById(bouquetId)
                .orElseThrow(BouquetNotFoundException::new));
        cartRepository.save(cart);
    }

    /**
     * Очистка корзины
     * @param cartId корзина для очистки
     */

    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.clear();
        cart.setBouquets(bouquets);
        cartRepository.save(cart);
    }


    public Cart getCartByUser(User currentUser) {
        return cartRepository.findByUser(currentUser);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}

package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.exceptions.*;
import ru.sergeich.diploma.repositories.BouquetRepository;
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

    /**
     * Создание корзины. С привязкой к пользователю
     * @param userId пользователь, которому будет привязана корзина
     */
    public void createCart(Long userId) {
        cartRepository.save(new Cart(
                userService.getUserById(userId)
                        .orElseThrow(UserNotFoundException::new)));
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
     * @param cartId корзина для обновления
     * @param bouquetId букет для добавления/удаления
     * @param isAdd true - добавление, false - удаление
     */

    public void updateCart(Long cartId, Long bouquetId, boolean isAdd) {
        Cart cart = getCartById(cartId);
        if (isAdd) {
            addBouquetToCart(cartId, bouquetId);
        } else {
            removeBouquetFromCart(cartId, bouquetId);
        }
    }

    /**
     * Добавление букета в корзину
     * @param cartId корзина для добавления
     * @param bouquetId букет для добавления
     */
    public void addBouquetToCart(Long cartId, Long bouquetId) {
        Cart cart = getCartById(cartId);
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.add(bouquetService.getBouquetById(bouquetId).orElseThrow(BouquetNotFoundException::new));
        cartRepository.save(cart);
    }

    /**
     * Удаление букета из корзины
     * @param cartId корзина для удаления
     * @param bouquetId букет для удаления
     */
    public void removeBouquetFromCart(Long cartId, Long bouquetId) {
        Cart cart = getCartById(cartId);
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.remove(bouquetService.getBouquetById(bouquetId).orElseThrow(BouquetNotFoundException::new));
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


}

package ru.sergeich.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.exceptions.*;
import ru.sergeich.diploma.repositories.CartRepository;
import ru.sergeich.diploma.services.AuthService;
import ru.sergeich.diploma.services.BouquetService;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final BouquetService bouquetService;
    private final AuthService authService;


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
        authService.updateAuthentication(user);

    }

    /**
     * Обновление корзины. Добавление/удаление букета
     * @param cart корзина
     * @param bouquetId букет для добавления/удаления
     */
    public void addBouquetToCart(Cart cart, Long bouquetId) {
        List <Bouquet> bouquets = cart.getBouquets();
        bouquets.add(bouquetService.getBouquetById(bouquetId)
                .orElseThrow(BouquetNotFoundException::new));
        cartRepository.save(cart);
        log.info("Добавлен букет в корзину");
    }

    /**
     * Удаление букета из корзины
     * @param cart корзина
     * @param bouquetId ид букета для удаления
     */
    public void removeBouquetFromCart(Cart cart, Long bouquetId) {
        List <Bouquet> bouquets = cart.getBouquets();
        Bouquet bouquet = bouquetService.getBouquetById(bouquetId)
                .orElseThrow(BouquetNotFoundException::new);
        boolean result = bouquets.remove(bouquet);
        if (result) log.info("Удален букет из корзины");
        else log.info("Букет не был удален из корзины");

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
        authService.updateAuthentication(cart.getUser());
        log.info("Корзина очищена");
    }

    /**
     * Получение корзины по пользователю
     * @param currentUser пользователь
     * @return корзина
     */


    public Cart getCartByUser(User currentUser) {
        return cartRepository.findByUser(currentUser);
    }

    /**
     * Сохранение корзины
     * @param cart корзина
     * @return сохраненная корзина
     */

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}

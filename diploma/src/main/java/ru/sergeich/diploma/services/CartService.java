package ru.sergeich.diploma.services;

import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;

public interface CartService {
    Cart createCart(User user);
    void deleteCart(Long cartId);
    Cart getCartById(Long cartId);
    void updateCart(User user, Long bouquetId, boolean isAdd);
    void addBouquetToCart(Cart cart, Long bouquetId);
    void removeBouquetFromCart(Cart cart, Long bouquetId);
    void clearCart(Long cartId);
    Cart getCartByUser(User currentUser);
    Cart saveCart(Cart cart);
}

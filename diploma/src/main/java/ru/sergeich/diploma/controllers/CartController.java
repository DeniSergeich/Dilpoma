package ru.sergeich.diploma.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Cart cart = cartService.getCartByUser(currentUser);
        if (cart == null) {
            cart = cartService.createCart(currentUser);
        }

        return ResponseEntity.ok(cart);
    }


}
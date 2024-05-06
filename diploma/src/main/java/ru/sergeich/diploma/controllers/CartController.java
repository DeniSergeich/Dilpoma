package ru.sergeich.diploma.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class CartController {
@Autowired
    private CartService cartService;
@Autowired
    private UserService userService;

@GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal User user, Model model) {

    updateUserCart(user);
    Cart cart = cartService.getCartById(user.getCart().getId());
    log.info("Cart: {} User: {}", cart.getId(), user.getId());

    Map<Bouquet, Long> bouquetsMap = cart.getBouquets().stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


    model.addAttribute("bouquets", bouquetsMap);
    model.addAttribute("cart", cart);

        return "cart";

    }



    @GetMapping("/cart/clear")
    public String clearCart(@AuthenticationPrincipal User user,Model model) {
        cartService.clearCart(user.getCart().getId());
        Cart updatedCart = cartService.getCartById(user.getCart().getId());
        model.addAttribute("cart", updatedCart);
        log.info("Model cart: {}", model.getAttribute("cart"));
        
        return "redirect:/cart";
    }
    private void updateUserCart(User user) {
        if (user.getCart() == null) {
            log.info("User's cart is null");
            user.setCart(cartService.createCart(user));
            userService.saveUser(user);
        }
    }


}
package ru.sergeich.diploma.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.BouquetService;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;


import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {

    private final BouquetService bouquetService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/add_bouquet/{bouquetId}")
    public String addB(@AuthenticationPrincipal User user, @PathVariable long bouquetId){
        cartService.updateCart(user, bouquetId, true);
        return "redirect:/shop";
    }

    @GetMapping("/rem_bouquet/{bouquetId}")
    public String removeB(@AuthenticationPrincipal User user, @PathVariable long bouquetId){
        cartService.updateCart(user, bouquetId, false);
        return "redirect:/shop";
    }
    @GetMapping("/shop")
    public String getShop(@AuthenticationPrincipal User user, Model model) {
        if (user != null ) {
            updateUserCart(user);
            Cart cart = cartService.getCartById(user.getCart().getId());
            log.info("Cart: {} User: {}", cart.getId(), user.getId());
            model.addAttribute("cart", cart);
        }
        List<Bouquet> bouquets = bouquetService.getAllBouquets();
        model.addAttribute("bouquets", bouquets);
        return (user != null) ? "shop" : "shop-unregistered";
    }

    private void updateUserCart(User user) {
        if (user.getCart() == null) {
            log.info("User's cart is null");
            user.setCart(cartService.createCart(user));
            userService.saveUser(user);
        }
    }
}

package ru.sergeich.diploma.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.*;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
@Autowired
    private CartService cartService;
@Autowired
    private UserService userService;

@Autowired
private OrderService orderService;


private final MailSenderService mailSenderService;

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

    @GetMapping("/cart/order")
    public String orderCart(@AuthenticationPrincipal User user) {
        Order order = orderService.createOrder(user);
        String body =  mailSenderService.createBody(user);
        log.info("Body: {}", body);
        mailSenderService.sendMail(user.getEmail(), order.getId(), mailSenderService.createBody(user));
        cartService.clearCart(user.getCart().getId());
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
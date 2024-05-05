package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.List;


@Controller
public class CartController {
@Autowired
    private CartService cartService;
@Autowired
    private UserService userService;

@GetMapping("/cart")
    public String getCart(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartService.getCartById(user.getCart().getId());
        model.addAttribute("cart", cart);
        return "cart";

    }



    @GetMapping("/cart/clear")
    public String clearCart(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.clearCart(user.getCart().getId());
        Cart updatedCart = cartService.getCartById(user.getCart().getId());
        model.addAttribute("cart", updatedCart);
        return "redirect:/cart";
    }


}
package ru.sergeich.diploma.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.BouquetService;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;
import ru.sergeich.diploma.exceptions.BouquetNotFoundException;

import java.util.List;
@Slf4j
@Controller
public class ShopController {

    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private  UserService userService;
    @Autowired
    private CartService cartService;



    
    @GetMapping("/add_bouquet/{bouquetId}")
    public String addB(@PathVariable Long bouquetId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.updateCart(user, bouquetId, true);
        return "redirect:/shop";
    }

    @GetMapping("/rem_bouquet/{bouquetId}")
    public String removeB(@PathVariable Long bouquetId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.updateCart(user, bouquetId, false);
        return "redirect:/shop";
    }
    @GetMapping("/shop")
    public String getShop(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            log.info("User: {}", user.getId());

            if(user.getCart() == null) {
                log.info("User's cart is null");
                user.setCart(cartService.createCart(user));
                userService.saveUser(user);
            }

            Cart cart = cartService.getCartByUser(user);
            log.info("Cart: {} User: {}", cart.getId(), user.getId());

            model.addAttribute("cart", cart);
            List<Bouquet> bouquets = bouquetService.getAllBouquets();
            model.addAttribute("bouquets", bouquets);
            return "shop";
        }
        List<Bouquet> bouquets = bouquetService.getAllBouquets();
        model.addAttribute("bouquets", bouquets);
        return "shop-unregistered";
    }

}

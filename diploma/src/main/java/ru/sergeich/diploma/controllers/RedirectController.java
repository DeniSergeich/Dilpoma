package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.BouquetService;

import java.util.List;

@Controller
public class RedirectController {
    @Autowired
    private BouquetService bouquetService;

    @GetMapping("/contacts")
    public String getContacts() {
        return "contacts";
    }

    @GetMapping("/masters")
    public String getMasters() {
        return "masters";
    }

    /**
     * Извлекает страницу входа, если пользователь не прошел аутентификацию, в противном случае перенаправляет на домашнюю страницу.
     *
     * @return страницу входа, если пользователь не аутентифицирован, в противном случае перенаправляется на домашнюю страницу.
     * Исключение @throws, если возникает ошибка при получении контекста аутентификации.
     */
    @GetMapping("/login")
    public String getLogin() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/index")
    public String getHome(){
        return "index";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }

    @GetMapping("/lk")
    public String getLk(){
        return "lk";
    }

    @GetMapping("/shop")
    public String getShop(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            user.setCart(new Cart(user));
            List<Bouquet> bouquets = bouquetService.getAllBouquets();
            model.addAttribute("bouquets", bouquets);
            return "shop";
        }
        List<Bouquet> bouquets = bouquetService.getAllBouquets();
        model.addAttribute("bouquets", bouquets);
        return "shop-unregistered";
    }
    @GetMapping("/cart")
    public String getCart(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = user.getCart();
//        List<Bouquet> bouquets = cart.getBouquets();
//        model.addAttribute("bouquets", bouquets);
        model.addAttribute("cart", cart);
        return "cart";

    }

}

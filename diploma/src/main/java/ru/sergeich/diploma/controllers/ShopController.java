package ru.sergeich.diploma.controllers;

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

@Controller
public class ShopController {

    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private  UserService userService;
    @Autowired
    private CartService cartService;

//    public ShopController(BouquetService bouquetService) {
//        this.bouquetService = bouquetService;
//    }

//    @GetMapping("/shop")
//    public String getShop(Model model) {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////
////        if (auth instanceof AnonymousAuthenticationToken) {
////            return "redirect:/login";
////        }
//
//        List<Bouquet> bouquets = bouquetService.getAllBouquets();
//        model.addAttribute("bouquets", bouquets);
//        return "shop";
//    }
    
    @GetMapping("/add_bouquet/{bouquetId}")
    public String addB(@PathVariable Long bouquetId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.updateCart(user, bouquetId, true);
        return "redirect:/shop";
    }
}

package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;


@Controller
public class ShopController {

    @Autowired
    private CartService cartService;



    @GetMapping("/add_b{number:\\d+}")
    public String addB(@PathVariable int number){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cartService.workWithCart((User) auth.getPrincipal(), number, true);
        return "redirect:/shop#b" + String.valueOf(number);
    }

    @GetMapping("/rem_b{number:\\d+}")
    public String remB(@PathVariable int number){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cartService.workWithCart((User) auth.getPrincipal(), number, false);
        return "redirect:/shop#b" + String.valueOf(number);
    }


}


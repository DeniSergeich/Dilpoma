package ru.sergeich.diploma.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

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

    /**
     * Извлекает страницу магазина на основе текущего статуса аутентификации.
     *
     * @return страницу магазина, если пользователь аутентифицирован, в противном случае страница shop_unregistered
     */
    @GetMapping("/shop")
    public String getShop(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "shop";
        }
        return "shop_unregistered";
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

}

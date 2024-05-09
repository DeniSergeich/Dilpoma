package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sergeich.diploma.services.BouquetService;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RedirectController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BouquetService bouquetService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/contacts")
    public String getContacts() {
        return "contacts";
    }

    @GetMapping("/masters")
    public String getMasters() {
        return "masters";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }
    @GetMapping("/index")
    public String getHome(){
        return "index";
    }


    /**
     * Извлекает страницу входа, если пользователь не прошел аутентификацию, в противном случае перенаправляет на домашнюю страницу.
     *
     * @return страницу входа, если пользователь не аутентифицирован, в противном случае перенаправляется на домашнюю страницу.
     * Исключение @throws, если возникает ошибка при получении контекста аутентификации.
     */
    @GetMapping("/login")
    public String getLogin() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Пользователь аутентифицирован, выполняем редирект на ту страницу, с которой пришел
                String referer = request.getHeader("Referer");
                return "redirect:" + referer;
            }
        }
        return "login";
    }




//
//    @GetMapping("/lk")
//    public String getLk(){
//        return "lk";
//    }

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect:/shop";
    }
    @GetMapping("/referer")
    public String referer(@RequestParam(required = false) String referer, HttpServletRequest request) {
        if (referer != null) {
            // Redirect to the previous URL if available
            return "redirect:" + referer;
        } else {
            // Redirect to the default URL if no previous URL is available
            return "redirect:/shop";
        }
    }


}

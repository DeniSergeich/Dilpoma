package ru.sergeich.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final HttpServletRequest request;

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

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect:/shop";
    }
}

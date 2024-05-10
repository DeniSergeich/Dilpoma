package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * Получает регистрационную форму для нового пользователя. Если пользователь уже прошел аутентификацию, происходит перенаправление на домашнюю страницу.
     *
     * @param model модель, содержащая атрибуты представления.
     * @return имя представления для регистрационной формы
     */
    @GetMapping("/register")
    public String getForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Сохраняет регистрационные данные пользователя. Проверяет входные пользовательские данные и выполняет необходимую обработку ошибок.
     *
     * @param user  объект пользователя, содержащий регистрационные данные
     * @param model модель для добавления атрибутов для обработки ошибок и перенаправления.
     * @return имя представления, куда пользователь должен быть перенаправлен в зависимости от результата регистрации.
     */
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") @Valid User user, Model model) {
        if (user.getUsername().isEmpty()) {
            model.addAttribute("errorLenUsername", true);
            return "register";
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            model.addAttribute("errorConfPassword", true);
            return "register";
        }
        if (user.getPassword().length() < 5) {
            model.addAttribute("errorLenPassword", true);
            return "register";
        }
        if (userService.findUserByUsername(user.getUsername()) != null) {
            model.addAttribute("errorAlreadyExistsUsername", true);
            return "register";
        }
        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorSave", true);
            return "register";
        }
    }
}

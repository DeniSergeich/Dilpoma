package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.UserService;


import java.util.Objects;

@Controller
public class LKController {

    @Autowired
    private UserService userService;

    public Model addInfoAboutSession(Model model){
        model.addAttribute("infoSetting", true);
        model.addAttribute("infoMessage", "После смены данных вы выйдете из своего личного кабинета");
        return model;
    }

    @GetMapping("/lk-username")
    public String chUser(Model model){
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("changeUsername", true);
        model = addInfoAboutSession(model);
        return "lk";
    }

    @PostMapping("/lk-username")
    public String confUser(@ModelAttribute("username") String username, Model model){
        if (username.length() < 5){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Имя пользователя должно быть не менее 5 символов");
            return "lk";
        }
        else if (userService.findUserByUsername(username) != null){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пользователь с таким ником уже существует");
            return "lk";
        }
        User changed_user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        changed_user.setUsername(username);
        userService.rootReSaveUser(changed_user);
        return "redirect:/logout";
    }

    @GetMapping("/lk-password")
    public String chPass(Model model){
        model.addAttribute("changePassword", true);
        model = addInfoAboutSession(model);
        return "lk";
    }

    @PostMapping("/lk-password")
    public String confPass(@ModelAttribute("newPassword") String newPassword,
                           @ModelAttribute("newPasswordConfirm") String newPasswordConfirm, Model model){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (newPassword.length() < 5){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Новый пароль должен быть не менее 5 символов");
            return "lk";
        }
        else if (!newPassword.equals(newPasswordConfirm)){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Новые пароли не совпадают");
            return "lk";
        }
        current_user.setPassword(newPassword);
        current_user.setPasswordConfirm(newPasswordConfirm);
        userService.rootReSaveUserWithPassword(current_user);
        return "redirect:/logout";
    }

    @GetMapping("/lk-email")
    public String chEmail(Model model){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("changeEmail", true);
        model.addAttribute("email", current_user.getEmail());
        model = addInfoAboutSession(model);
        return "lk";
    }

    @PostMapping("/lk-email")
    public String chEmail(@ModelAttribute("email") String email, Model model){
        User current_user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        current_user.setEmail(email);
        userService.rootReSaveUser(current_user);
        return "redirect:/logout";
    }

}


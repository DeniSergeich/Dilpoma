package ru.sergeich.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.UserService;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Добавляет информацию о сеансе в данную модель.
     *
     * @param  model  модель для добавления информации о сеансе
     * @return        обновленная модель с добавленной информацией о сеансе
     */
    public Model addInfoAboutSession(Model model) {
        model.addAttribute("infoSetting", true);
        model.addAttribute("infoMessage", "После смены данных вы будете перенаправлены на страницу авторизации.");
        return model;
    }

    /**
     * Извлекает имя текущего пользователя, прошедшего проверку подлинности, и устанавливает его в качестве атрибута в модели.
     * Устанавливает флаг, указывающий, что имя пользователя меняется.
     * Добавляет информацию о сеансе в модель.
     * Возвращает имя представления «lk».
     *
     * @param  model  объект модели для хранения атрибутов
     * @return        имя представления "lk"
     */
    @GetMapping("/lk-username")
    public String changeUser(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("changeUsername", true);
        model = addInfoAboutSession(model);
        return "lk";
    }

    /**
     * Обновляет имя пользователя для текущего аутентифицированного пользователя.
     *
     * @param  username  новое имя пользователя, которое будет установлено
     * @param  model     объект модели для хранения атрибутов
     * @return           имя представления для страницы ЛК или перенаправление на страницу выхода из системы
     */
    @PostMapping("/lk-username")
    public String confirmUsername(@ModelAttribute("username") String username, Model model) {
        if (username.length() < 3){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Имя пользователя не может быть менее трех символов.");
            return "lk";
        }
        else if (userService.findUserByUsername(username) != null){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пользователь с таким именем уже существует.");
            return "lk";
        }
        User currentUser = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        currentUser.setUsername(username);
        userService.saveUser(currentUser);
        model = addInfoAboutSession(model);
        return "redirect:/logout";
    }

    /**
     * Извлекает пароль текущего пользователя, прошедшего проверку подлинности, и устанавливает его в качестве атрибута в модели.
     * Устанавливает флаг, указывающий, что пароль пользователя меняется.
     * Добавляет информацию о сеансе в модель.
     * Возвращает имя представления «lk».
     * @param model объект модели для хранения атрибутов
     * @return имя представления "lk"
     */
    @GetMapping("/lk-password")
    public String changePassword(Model model) {
        model.addAttribute("changePassword", true);
        model = addInfoAboutSession(model);
        return "lk";
    }

    /**
     * Обновляет пароль для текущего аутентифицированного пользователя.
     *
     * @param newPassword новый пароль, который нужно установить.
     * @param newPasswordConfirm подтверждение нового пароля
     * @param model объект модели для передачи данных в представление
     * @return имя представления "lk"
     */
    @PostMapping("/lk-password")
    public String confirmPassword(@AuthenticationPrincipal User user, @ModelAttribute("newPassword") String newPassword,
                                  @ModelAttribute("newPasswordConfirm") String newPasswordConfirm, Model model) {
        if (newPassword.length() < 5){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароль должен содержать не менее 5 символов.");
            return "lk";
        } else if (!newPassword.equals(newPasswordConfirm)) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароли не совпадают.");
            return "lk";
        }
        user.setPassword(newPassword);
        user.setPasswordConfirm(newPasswordConfirm);
        userService.rootResaveUserWithPassword(user);
        return "redirect:/logout";
    }

    /**
     * Получает адрес электронной почты текущего пользователя и устанавливает его в качестве атрибута в модели.
     * Также устанавливает флаг, указывающий, что адрес электронной почты изменяется.
     * Добавляет информацию о сеансе в модель.
     *
     * @param model объект модели для хранения атрибутов
     * @return имя представления "lk"
     */
    @GetMapping("/lk-email")
    public String changeEmail(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("changeEmail", true);
        model.addAttribute("email", user.getEmail());
        model = addInfoAboutSession(model);
        return "lk";
    }

    /**
     * Обновляет адрес электронной почты для текущего аутентифицированного пользователя.
     *
     * @param email новый адрес электронной почты, который нужно установить.
     * @param model объект модели для передачи данных в представление
     * @return имя представления "lk"
     */
    @PostMapping("/lk-email")
    public String confirmEmail(@ModelAttribute("email") String email,@AuthenticationPrincipal User user,  Model model) {
        user.setEmail(email.toLowerCase());
        userService.saveUser(user);
        return "redirect:/logout";
    }

    @GetMapping("/lk")
    public String lk(@AuthenticationPrincipal User user, Model model) {
       Set<Order> orders = user.getOrders();
       model.addAttribute("orders", orders);
       return "lk";
    }
}

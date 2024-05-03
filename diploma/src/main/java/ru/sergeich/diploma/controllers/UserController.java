package ru.sergeich.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Возвращает имя представления «lk».
     *
     * @param  model  объект модели для хранения атрибутов
     * @return        имя представления "lk"
     */
    @GetMapping("/lk")
    public String lk(Model model) {
        model = addInfoAboutSession(model);
        return "lk";
    }

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
        if (username.length() < 1){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Имя пользователя не может быть пустым.");
            return "lk";
        }
        else if (userService.findUserByUsername(username) != null){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пользователь с таким именем уже существует.");
            return "lk";
        }
        User currentUser = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        currentUser.setUsername(username);
        userService.rootResaveUser(currentUser);
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
    public String confirmPassword(@ModelAttribute("newPassword") String newPassword,
                                  @ModelAttribute("newPasswordConfirm") String newPasswordConfirm, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (newPassword.length() < 5){
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароль должен содержать не менее 5 символов.");
            return "lk";
        } else if (!newPassword.equals(newPasswordConfirm)) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароли не совпадают.");
            return "lk";
        }
        currentUser.setPassword(newPassword);
        currentUser.setPasswordConfirm(newPasswordConfirm);
        userService.rootResaveUserWithPassword(currentUser);
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
    public String changeEmail(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("changeEmail", true);
        model.addAttribute("email", currentUser.getEmail());
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
    public String confirmEmail(@ModelAttribute("email") String email, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser.setEmail(email);
        userService.rootResaveUser(currentUser);
        return "redirect:/logout";
    }


}

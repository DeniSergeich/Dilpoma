package ru.sergeich.diploma.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.*;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    private final OrderService orderService;

    private final MailSenderService mailSenderService;

   /**
     * Извлекает корзину пользователя и связанные с ней букеты, обновляет атрибуты модели и возвращает имя представления «корзина».
     *
     * @param user — аутентифицированный пользователь
     * @param model модель для добавления атрибутов
     * @return представление cart
     */
    @GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal User user, Model model) {
    updateUserCart(user);
    Cart cart = cartService.getCartById(user.getCart().getId());
    log.info("Cart: {} User: {}", cart.getId(), user.getId());
    Map<Bouquet, Long> bouquetsMap = cart.getBouquets().stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    model.addAttribute("bouquets", bouquetsMap);
    model.addAttribute("cart", cart);
        return "cart";
    }

    /**
     * Очищает корзину текущего пользователя.
     *
     * @param user аутентифицированный пользователь
     * @param model описание параметра
     * @return возвращает редирект на cart
     */
    @GetMapping("/cart/clear")
    public String clearCart(@AuthenticationPrincipal User user,Model model) {
        cartService.clearCart(user.getCart().getId());
        Cart updatedCart = cartService.getCartById(user.getCart().getId());
        model.addAttribute("cart", updatedCart);
        log.info("Model cart: {}", model.getAttribute("cart"));
        return "redirect:/cart";
    }

    /**
     * Создает заказ и отправляет письмо пользователю. Очищает корзину
     *
     * @param user аутентифицированный пользователь
     * @param redirectAttributes атрибуты редиректа
     * @return возвращает редирект на cart
     */
    @GetMapping("/cart/order")
    public String orderCart(@AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        Order order = orderService.createOrder(user);
        String body = mailSenderService.createBody(user);
        log.info("Body: {}", body);
        mailSenderService.sendMail(user.getEmail(), order.getId(), body);
        cartService.clearCart(user.getCart().getId());
        redirectAttributes.addFlashAttribute("infoSetting", true);
        redirectAttributes.addFlashAttribute("infoMessage", "Информация о заказе отправлена на " + user.getEmail());
        return "redirect:/cart";
    }

    /**
     * Присваивает пользователю новую корзину.
     *
     * @param user аутентифицированный пользователь
     */
    private void updateUserCart(User user) {
        if (user.getCart() == null) {
            log.info("User's cart is null");
            user.setCart(cartService.createCart(user));
            userService.saveUser(user);
        }
    }
}
package ru.sergeich.diploma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sergeich.diploma.controllers.CartController;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Cart;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.OrderService;
import ru.sergeich.diploma.services.UserService;
import ru.sergeich.diploma.services.MailSenderService;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartControllerTest {

    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private Model model;
    @Mock
    private RedirectAttributes redirectAttributes;

    private User user = new User();
    private Cart cart = new Cart();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartController = new CartController(cartService, userService, orderService, mailSenderService);
        user = new User();
        user.setId(1L);
        cart = new Cart();
        cart.setId(1L);
        user.setCart(cart);
    }


    @Test
    void testGetCart() {

        Bouquet bouquet = new Bouquet();
        bouquet.setId(1L);

        cart.setBouquets(Collections.singletonList(bouquet));

        when(cartService.getCartById(1L)).thenReturn(cart);

        Map<Bouquet, Long> bouquetsMap = Collections.singletonMap(bouquet, 1L);

        String viewName = cartController.getCart(user, model);

        assertEquals("cart", viewName);
        assertEquals(1, bouquetsMap.size());
        assertEquals(1, bouquetsMap.get(bouquet));
    }
    @Test
    void testOrderCart() {
        // given
        user.setEmail("test@example.com");
        Order order = new Order();
        // when
        when(orderService.createOrder(user)).thenReturn(order);
        when(mailSenderService.createBody(user)).thenReturn("Test body");
        String viewName = cartController.orderCart(user, redirectAttributes);
        // then
        verify(orderService).createOrder(user);
        verify(mailSenderService).createBody(user);
        verify(mailSenderService).sendMail(user.getEmail(), order.getId(), "Test body");
        verify(cartService).clearCart(cart.getId());
        verify(redirectAttributes).addFlashAttribute("infoSetting", true);
        verify(redirectAttributes).addFlashAttribute("infoMessage", "Информация о заказе отправлена на test@example.com");
    }

    @Test
    void testClearCart() {


        when(cartService.getCartById(1L)).thenReturn(cart);

        String viewName = cartController.clearCart(user, model);

        verify(cartService).clearCart(1L);
        verify(cartService).getCartById(1L);
        verify(model).addAttribute("cart", cart);
        verify(model).getAttribute("cart");

    }
}
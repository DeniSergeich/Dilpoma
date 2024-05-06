package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositories.OrderRepository;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(User user) {
        List<Bouquet> bouquet = user.getCart().getBouquets();
        Order order = orderRepository.save(new Order(user, bouquet));
        authService.updateAuthentication(user);
        return order;
    }
}

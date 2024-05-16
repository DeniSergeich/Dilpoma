package ru.sergeich.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;
import ru.sergeich.diploma.repositories.OrderRepository;
import ru.sergeich.diploma.services.AuthService;
import ru.sergeich.diploma.services.OrderService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;

    public Order createOrder(User user) {
        List<Bouquet> bouquet = user.getCart().getBouquets();
        Order order = orderRepository.save(new Order(user, bouquet));
        authService.updateAuthentication(user);
        return order;
    }
}

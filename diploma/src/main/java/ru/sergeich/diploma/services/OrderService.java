package ru.sergeich.diploma.services;

import ru.sergeich.diploma.domain.Order;
import ru.sergeich.diploma.domain.User;

public interface OrderService {
    Order createOrder(User user);
}

package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
}

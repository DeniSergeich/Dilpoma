package ru.sergeich.diploma.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.services.BouquetService;
import ru.sergeich.diploma.services.CartService;
import ru.sergeich.diploma.services.UserService;

@Service
public class ShopServiceImpl {

    //Ваще нахуй не нужен
    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
}

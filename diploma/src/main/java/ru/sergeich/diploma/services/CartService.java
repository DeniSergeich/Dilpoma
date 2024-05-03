package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.repositories.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
}

package ru.sergeich.diploma.services;

import ru.sergeich.diploma.domain.User;

public interface AuthService {
    void updateAuthentication(User user);
}

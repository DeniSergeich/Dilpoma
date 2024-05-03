package ru.sergeich.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

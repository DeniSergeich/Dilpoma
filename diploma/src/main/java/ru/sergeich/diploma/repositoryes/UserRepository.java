package ru.sergeich.diploma.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

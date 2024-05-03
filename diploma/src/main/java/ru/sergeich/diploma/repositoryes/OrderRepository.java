package ru.sergeich.diploma.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergeich.diploma.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

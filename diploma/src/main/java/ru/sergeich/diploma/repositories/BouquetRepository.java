package ru.sergeich.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeich.diploma.domain.Bouquet;

@Repository
public interface BouquetRepository extends JpaRepository<Bouquet, Long> {
    Bouquet findById(int id);
}

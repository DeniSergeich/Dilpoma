package ru.sergeich.diploma.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeich.diploma.domain.Bouquet;

import java.util.Optional;
@Repository
public interface BouquetRepository extends JpaRepository<Bouquet, Long> {
    Bouquet findById(int id);
}

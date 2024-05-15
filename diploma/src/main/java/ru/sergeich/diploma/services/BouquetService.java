package ru.sergeich.diploma.services;

import ru.sergeich.diploma.domain.Bouquet;

import java.util.List;
import java.util.Optional;

public interface BouquetService {
    Optional<Bouquet> getBouquetById(Long bouquetId);
    List<Bouquet> getAllBouquets();
    void addBouquet(Bouquet bouquet);
    void updateBouquet(Long id, Bouquet updatedBouquet);
    void deleteBouquet(Long id);
}

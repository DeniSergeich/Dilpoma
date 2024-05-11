package ru.sergeich.diploma.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.repositories.BouquetRepository;
import ru.sergeich.diploma.services.BouquetService;

import java.util.List;
import java.util.Optional;

@Service
public class BouquetServiceImpl implements BouquetService {

    @Autowired
    private BouquetRepository bouquetRepository;

    /**
     * Получение букета по айди
     * @param bouquetId ид букета
     * @return букет
     */
    public Optional<Bouquet> getBouquetById(Long bouquetId) {

        return bouquetRepository.findById(bouquetId);
    }

    /**
     * Получение всех букетов
     * @return список букетов
     */
    public List<Bouquet> getAllBouquets() {
        return bouquetRepository.findAll();
    }

    /**
     * Добавление букета
     * @param bouquet букет
     */

    public void addBouquet(Bouquet bouquet) {
        bouquetRepository.save(bouquet);
    }

    /**
     * Обновить букет
     * @param id ид букета для обновления
     * @param updatedBouquet новый букет
     */
    public void updateBouquet(Long id, Bouquet updatedBouquet) {
        bouquetRepository.save(updatedBouquet);
    }

    /**
     * Удалить букет
     * @param id ид букета
     */
    public void deleteBouquet(Long id) {
        bouquetRepository.deleteById(id);
    }


}

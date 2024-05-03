package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.repositories.BouquetRepository;

import java.util.Optional;

@Service
public class BouquetService {

    @Autowired
    private BouquetRepository bouquetRepository;

    public Optional<Bouquet> getBouquetById(Long bouquetId) {

        return bouquetRepository.findById(bouquetId);
    }
}

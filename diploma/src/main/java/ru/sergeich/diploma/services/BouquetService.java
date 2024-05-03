package ru.sergeich.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeich.diploma.repositoryes.BouquetRepository;

@Service
public class BouquetService {

    @Autowired
    private BouquetRepository bouquetRepository;
}

package ru.sergeich.diploma.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BouquetNotFoundException extends RuntimeException {

    public BouquetNotFoundException() {
        super();
        log.error("Букет не найден");
    }

}

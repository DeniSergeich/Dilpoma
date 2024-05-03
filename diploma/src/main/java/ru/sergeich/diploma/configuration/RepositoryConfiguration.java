package ru.sergeich.diploma.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sergeich.diploma.domain.Bouquet;
import ru.sergeich.diploma.repositories.BouquetRepository;

@Configuration
public class RepositoryConfiguration {
    @Bean
    public CommandLineRunner dataLoader(BouquetRepository repository) {

        return args -> {
            repository.save(new Bouquet(1L, "Букет Микс", 10, 1500.0, "description", "/static/bouquet_1.jpg"));
            repository.save(new Bouquet(2L, "Букет Комплимент", 10, 1800.0, "description", "/static/bouquet_2.jpg"));
            repository.save(new Bouquet(3L, "Букет Свадебный", 10, 2300.0, "description", "/static/bouquet_3.jpg"));
            repository.save(new Bouquet(4L, "Букет Джентельмен", 10, 5000.0, "description", "/static/bouquet_4.jpg"));
            repository.save(new Bouquet(5L, "Букет Вьюга", 10, 3100.0, "description", "/static/bouquet_5.jpg"));
            repository.save(new Bouquet(6L, "Букет Джунгли", 10, 2800.0, "description", "/static/bouquet_6.jpg"));
            repository.save(new Bouquet(7L, "Букет Восход", 10, 1900.0, "description", "/static/bouquet_7.jpg"));
            repository.save(new Bouquet(8L, "Букет Стихия", 10, 2600.0, "description", "/static/bouquet_8.jpg"));
            repository.save(new Bouquet(9L, "Букет Удача", 10, 2400.0, "description", "/static/bouquet_9.jpg"));
            repository.save(new Bouquet(10L, "Неизвестный букет", 10, 10.0, "description", "/static/icon.jpg"));
        };
    }
}

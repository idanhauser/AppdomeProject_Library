package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {

        return args -> {
            log.info("Database initialized.");
            log.info("Preloading " + repository.save(new Book("ProgramingC", "Idan hauser",13)));
            log.info("Preloading " + repository.save(new Book("daibd", "frannco",68)));

        };
    }
}

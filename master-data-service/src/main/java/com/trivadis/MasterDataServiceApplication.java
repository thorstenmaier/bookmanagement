package com.trivadis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MasterDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterDataServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(new Book("Microservices: Grundlagen flexibler Softwarearchitekturen", new Author("Eberhard Wolff")));
            bookRepository.save(new Book("Building Microservices", new Author("Sam Newman")));
        };
    }

}

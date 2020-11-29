package com.trivadis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
@EnableJms
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

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}

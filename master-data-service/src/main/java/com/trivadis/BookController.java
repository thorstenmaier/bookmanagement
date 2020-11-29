package com.trivadis;

import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BookRepository bookRepository;
    private JmsTemplate jmsTemplate;

    public BookController(BookRepository bookRepository, JmsTemplate jmsTemplate) {
        this.bookRepository = bookRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/book")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        ResponseEntity<Book> entity = ResponseEntity.ok(bookRepository.save(book));
        jmsTemplate.convertAndSend("newBookTopic", new AddedBookEvent(book.getName(), book.getAuthor().getName()));
        return entity;
    }
}

package com.trivadis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookRepository.save(book));
    }
}

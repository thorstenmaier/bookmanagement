package com.trivadis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    private TreeSet<Book> allBooks = new TreeSet<>();

    public SearchController() {
        allBooks.add(new Book("Microservices: Grundlagen flexibler Softwarearchitekturen", "Eberhard Wolff"));
        allBooks.add(new Book("Building Microservices", "Sam Newman"));
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam("q") String q) {
        return allBooks.stream().filter(b -> b.getName().contains(q)).limit(10).collect(Collectors.toList());
    }
}
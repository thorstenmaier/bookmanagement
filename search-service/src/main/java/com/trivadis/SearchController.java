package com.trivadis;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jms.annotation.JmsListener;
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

    public SearchController(DiscoveryClient discoveryClient) {
        List<ServiceInstance> instances = discoveryClient.getInstances("master-data-service");
        String uri = instances.stream().findFirst().get().getUri().toString();
        Arrays.stream(new RestTemplate().getForObject(uri + "/book", Book[].class)).forEach(allBooks::add);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam("q") String q) {
        return allBooks.stream().filter(b -> b.getName().contains(q)).limit(10).collect(Collectors.toList());
    }

    @JmsListener(destination = "newBookTopic")
    public void receiveBook(AddedBookEvent addedBookEvent) {
        System.out.println("New book added");
        allBooks.add(new Book(addedBookEvent.getName(), addedBookEvent.getAuthor()));
    }
}

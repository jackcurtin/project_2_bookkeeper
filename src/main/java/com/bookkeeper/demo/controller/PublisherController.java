package com.bookkeeper.demo.controller;

import com.bookkeeper.demo.model.Book;
import com.bookkeeper.demo.model.Genre;
import com.bookkeeper.demo.model.Publisher;
import com.bookkeeper.demo.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private PublisherService publisherService;

    @Autowired
    public void setPublisherService(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    //http://localhost:9091/api/publishers
    @GetMapping
    public List<Publisher> getAllPublishers(){
        System.out.println("calling getAllPublishers");
        return publisherService.getAllPublishers();
    }

    //http://localhost:9091/api/publishers/2
    @GetMapping("/{publisherId}")
    public Publisher getPublisher(@PathVariable Long publisherId){
        System.out.println("calling getPublisher");
        return publisherService.getPublisher(publisherId);
    }

    //http://localhost:9091/api/publishers/add/
    @PostMapping("/add")
    public Publisher addPublisher(@RequestBody Publisher publisherObject){
        System.out.println("Calling addPublisher");
        return publisherService.addPublisher(publisherObject);
    }

    //http://localhost:9091/api/publishers/delete/2
    @DeleteMapping("/delete/{publisherId}")
    public ResponseEntity<Object> deletePublisher(@PathVariable Long publisherId){
        System.out.println("Calling deletePublisher");
        return publisherService.deletePublisher(publisherId);
    }

    //http://localhost:9091/api/publishers/2/allBooks
    @GetMapping("/{publisherId}/allBooks")
    public List<Book> getAllBooksByPublisher(@PathVariable Long publisherId){
        System.out.println("Calling getAllBooksByPublisher");
        return publisherService.getAllBooksByPublisher(publisherId);
    }
}

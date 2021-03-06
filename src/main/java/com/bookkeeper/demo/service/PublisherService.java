package com.bookkeeper.demo.service;

import com.bookkeeper.demo.exception.CannotBeNullException;
import com.bookkeeper.demo.exception.InformationExistsException;
import com.bookkeeper.demo.exception.InformationNotFoundException;
import com.bookkeeper.demo.model.Book;
import com.bookkeeper.demo.model.Genre;
import com.bookkeeper.demo.model.Publisher;
import com.bookkeeper.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private PublisherRepository publisherRepository;

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository){
        this.publisherRepository = publisherRepository;
    }

    //Get the list of Publishers
    public List<Publisher> getAllPublishers(){
        System.out.println("service calling getAllPublishers");
        List<Publisher> publishers = publisherRepository.findAll();
        if(publishers.isEmpty()){
            throw new InformationNotFoundException("No publishers in the database");
        }
        return publishers;
    }

    //Get the single Publisher
    public Publisher getPublisher(Long publisherId){
        System.out.println("Service calling getPublisher");
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        if (publisher.isPresent()){
            return publisher.get();
        } else{
            throw new InformationNotFoundException("Publisher with ID " + publisherId + " not found");
        }
    }

    //Create the publisher
    public Publisher addPublisher(Publisher publisherObject){
        System.out.println("service calling addPublisher");
        Optional<Publisher> publisherChecker = publisherRepository.findByNameIgnoreCase(publisherObject.getName());
        if(publisherChecker.isPresent()){
            throw new InformationExistsException("Publisher named "+ publisherObject.getName() + " already in database.");
        }else{
            if(publisherObject.getName().length() <1){
                throw new CannotBeNullException("Publisher name cannot be null");
            } else {
                return publisherRepository.save(publisherObject);
            }
        }
    }

    //Delete the Publisher
    public ResponseEntity<Object> deletePublisher(Long publisherId){
        System.out.println("Service is calling deletePublisher");
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        if(publisher.isPresent()){
            publisherRepository.deleteById(publisherId);
            return ResponseEntity.ok(HttpStatus.OK);
        } else{
            throw new InformationNotFoundException("Publisher with ID "+ publisherId +" not found.");
        }
    }

    //Get the list of Books related to PublisherId
    public List<Book> getAllBooksByPublisher(Long publisherId) {
        System.out.println("Service calling getAllBooksByPublisher");
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        if (publisher.isPresent()) {
            return publisher.get().getBookList();
        } else {
            throw new InformationNotFoundException("Publisher with ID " + publisherId + " not found.");
        }
    }
}

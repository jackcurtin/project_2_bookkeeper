package com.bookkeeper.demo.service;

import com.bookkeeper.demo.exception.CannotBeNullException;
import com.bookkeeper.demo.exception.InformationExistsException;
import com.bookkeeper.demo.exception.InformationNotFoundException;
import com.bookkeeper.demo.model.Book;
import com.bookkeeper.demo.model.Genre;
import com.bookkeeper.demo.repository.GenreRepository;
import com.bookkeeper.demo.security.MyUserDetails;
import com.bookkeeper.demo.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    //Get the list of Genres
    public List<Genre> getAllGenres(){
        System.out.println("service calling all genre");
        List<Genre> genres = genreRepository.findAll();
        if(genres.isEmpty()){
            throw new InformationNotFoundException("There are no genres.");
        }
        return genres;
    }

    //Get the single Genre
    public Genre getGenre(Long genreId){
        System.out.println("service calling getGenre");
        Optional <Genre> genre = genreRepository.findById(genreId);
        if(genre.isPresent()){
            return genre.get();
        } else{
            throw new InformationNotFoundException("Genre with id "+ genreId + " not found");
        }
    }

    //Create the Genre
    public Genre addGenre(Genre genreObject){
        System.out.println("Service is calling addGenre");
        Optional<Genre> genreChecker = genreRepository.findByNameIgnoreCase(genreObject.getName());
        if (genreChecker.isPresent()){
            throw new InformationExistsException("Genre with name " + genreObject.getName()
                    + " already exists in this database");
        } else{
            if(genreObject.getName().length() <1){
                throw new CannotBeNullException("Genre name cannot be null");
            } else {
                return genreRepository.save(genreObject);
            }
        }
    }

    //Delete the Genre
    public ResponseEntity<Object> deleteGenre(Long genreId){
        System.out.println("Service is calling deleteGenre");
        Optional<Genre> genre = genreRepository.findById(genreId);
        if(genre.isPresent()){
            genreRepository.deleteById(genreId);
            return ResponseEntity.ok(HttpStatus.OK);
        } else{
            throw new InformationNotFoundException("Genre with ID "+ genreId +" not found.");
        }
    }

    //Get the list of Books related to the GenreId
    public List<Book> getAllBooksByGenre(Long genreId){
        System.out.println("Service is calling getAllBooksByGenre");
        Optional<Genre> genre = genreRepository.findById(genreId);
        if(genre.isPresent()){
            return genre.get().getBookList();
        } else{
            throw new InformationNotFoundException("Genre with ID "+ genreId +" not found.");
        }
    }
}

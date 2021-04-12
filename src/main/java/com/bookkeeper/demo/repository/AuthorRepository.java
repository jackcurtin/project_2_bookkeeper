package com.bookkeeper.demo.repository;

import com.bookkeeper.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
   Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}

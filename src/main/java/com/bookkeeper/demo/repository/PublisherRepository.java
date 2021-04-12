package com.bookkeeper.demo.repository;

import com.bookkeeper.demo.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository <Publisher, Long> {
    Optional<Publisher> findById(Long publisherId);
}

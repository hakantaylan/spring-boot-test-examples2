package com.example.nplusone.repository;

import com.example.nplusone.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MyBookRepository extends JpaRepository<Book, Long> {

    List<Book> findTop3ByIsbn(String isbn);
//    Book findDistinctFirstTitleOrderByTitle();

}

package com.example.nplusone.service;

import com.example.nplusone.model.Book;
import com.example.nplusone.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private MyBookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void getReferenceById(Long Id) {
        entityManager.getReference(Book.class, Id);
    }

    @Transactional
    public void findById(Long Id) {
        bookRepository.findById(Id);
    }

    @Transactional
    public void getById(Long Id) {
        bookRepository.getById(Id);
    }

    @Transactional
    public void findTop3(String isbn) {
        List<Book> books = bookRepository.findTop3ByIsbn(isbn);
//        Book distinctFirstByIsbn = bookRepository.findDistinctFirstTitleOrderByTitle();
        System.out.println("");
    }
}

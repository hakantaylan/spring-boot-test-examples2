package com.example.nplusone;

import com.example.nplusone.model.Book;
import com.example.nplusone.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testEmptyResultException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book book = bookRepository.readByTitle("foobar4");
        });
    }

    @Test
    void testNullParam() {
        Optional<Book> byTitle = bookRepository.getByTitle(null);
        assertFalse(byTitle.isPresent());
    }

    @Test
    void testNoException() {
        Optional<Book> foo = bookRepository.getByTitle("foo");
        assertFalse(foo.isPresent());
    }

    @Test
    void testNoExceptionFindByTitlr() {
        Optional<Book> foo = bookRepository.findBookByTitle("foo");
        assertFalse(foo.isPresent());
    }
}
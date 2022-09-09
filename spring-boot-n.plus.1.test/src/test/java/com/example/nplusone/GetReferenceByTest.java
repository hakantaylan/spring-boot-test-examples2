package com.example.nplusone;

import com.example.nplusone.config.TestDataSourceConfig;
import com.example.nplusone.model.Book;
import com.example.nplusone.repository.MyBookRepository;
import com.example.nplusone.service.BookService;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestDataSourceConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetReferenceByTest {

    @Autowired
    MyBookRepository bookRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    BookService bookService;

    private Long id = 0L;

    @BeforeAll
    void setup() {
        bookRepository.deleteAll();
        Book entity = new Book();
        entity.setIsbn("123");
        Book saved = bookRepository.save(entity);
        id = saved.getId();
    }

    @BeforeEach
    public void resetDataSourceCounter() {
        QueryCountHolder.clear();
    }

    @Test
    void testFindById() {
        Optional<Book> byId = bookRepository.findById(id);

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertTrue(byId.isPresent());
        assertEquals(1, selectCount);
    }

    @Test
    void testGetById() {
        Book byId = bookRepository.getById(id);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertNotNull(byId);
        assertEquals(0, selectCount);
    }

    @Test
    void testReferenceById() {
        Book byId = bookRepository.getReferenceById(id);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertNotNull(byId);
        assertEquals(0, selectCount);
    }

    @Test
    void testReferenceById2() {
        Book byId = entityManager.getReference(Book.class, id + 1);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertNotNull(byId);
        assertEquals(0, selectCount);
    }

    @Test
    void testReferenceByGeneratesProxy() {

        assertThrows(LazyInitializationException.class, () -> {
            Book byId = bookRepository.getReferenceById(id);
            byId.getIsbn();
        });

    }

    @Test
    void testServiceFindById() {
        bookService.findById(id);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertEquals(1, selectCount);
    }

    @Test
    void testServiceGetById() {
        bookService.getById(id);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertEquals(0, selectCount);
    }

    @Test
    void testServiceGetReferenceById() {
        bookService.getReferenceById(id);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
        assertEquals(0, selectCount);
    }

    @Test
    void aaaa() {
        bookService.findTop3("123");
    }
}
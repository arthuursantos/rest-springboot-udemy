package org.example.restspringbootudemy.mockito.services;

import org.example.restspringbootudemy.controllers.exceptions.RequiredObjectIsNullException;
import org.example.restspringbootudemy.mocks.MockBook;
import org.example.restspringbootudemy.repositories.BookRepository;
import org.example.restspringbootudemy.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        var entities = input.mockEntityList();
        when(repository.findAll()).thenReturn(entities);

        var response = service.findAll();
        assertNotNull(response);
        assertEquals(14, response.size());

        var entityTest = response.get(6);
        assertNotNull(entityTest);
        assertNotNull(entityTest.getKey());
        assertNotNull(entityTest.getLinks());
        assertTrue(response.toString().contains("links: [</api/book/v1/6>;rel=\"self\"]"));
        assertEquals("AuthorTest6", entityTest.getAuthor());
        assertEquals(LocalDate.of(2012, 12, 16), entityTest.getLaunch_date());
        assertEquals("TitleTest6", entityTest.getTitle());
        assertEquals(10.0, entityTest.getPrice());
    }

    @Test
    void findById() {
        var entity = input.mockEntity();
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var response = service.findById(1L);
        assertNotNull(response);
        assertNotNull(response);
        assertNotNull(response.getKey());
        assertNotNull(response.getLinks());
        assertTrue(response.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("AuthorTest1", response.getAuthor());
        assertEquals(LocalDate.of(2012, 12, 16), response.getLaunch_date());
        assertEquals("TitleTest1", response.getTitle());
        assertEquals(10.0, response.getPrice());
    }

    @Test
    void create() {
        var entity = input.mockEntity();
        var persisted = entity;
        persisted.setId(1L);
        when(repository.save(entity)).thenReturn(persisted);

        var vo = input.mockVO();
        var response = service.create(vo);
        assertNotNull(response);
        assertNotNull(response);
        assertNotNull(response.getKey());
        assertNotNull(response.getLinks());
        assertTrue(response.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("AuthorTest1", response.getAuthor());
        assertEquals(LocalDate.of(2012, 12, 16), response.getLaunch_date());
        assertEquals("TitleTest1", response.getTitle());
        assertEquals(10.0, response.getPrice());
    }

    @Test
    void update() {
        var entity = input.mockEntity();
        entity.setId(1L);
        var persisted = entity;
        when(repository.findById(1L)).thenReturn(Optional.of(persisted));
        when(repository.save(entity)).thenReturn(persisted);
        var vo = input.mockVO();
        vo.setKey(1L);
        var response = service.update(vo);
        assertNotNull(response);
        assertNotNull(response);
        assertNotNull(response.getKey());
        assertNotNull(response.getLinks());
        assertTrue(response.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("AuthorTest1", response.getAuthor());
        assertEquals(LocalDate.of(2012, 12, 16), response.getLaunch_date());
        assertEquals("TitleTest1", response.getTitle());
        assertEquals(10.0, response.getPrice());
    }

    @Test
    void delete() {
        var entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }

    @Test
    void createNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
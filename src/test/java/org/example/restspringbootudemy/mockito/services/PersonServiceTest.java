package org.example.restspringbootudemy.mockito.services;

import org.example.restspringbootudemy.controllers.exceptions.RequiredObjectIsNullException;
import org.example.restspringbootudemy.data.vo.v1.PersonVO;
import org.example.restspringbootudemy.entities.Person;
import org.example.restspringbootudemy.mocks.MockPerson;
import org.example.restspringbootudemy.repositories.PersonRepository;
import org.example.restspringbootudemy.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        var entities = input.mockEntityList();
        when(repository.findAll()).thenReturn(entities);

        var result = service.findAll();
        assertNotNull(result);
        assertEquals(14, result.size());

        var entityTest = result.get(6);
        assertNotNull(entityTest);
        assertNotNull(entityTest.getKey());
        assertNotNull(entityTest.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/6>;rel=\"self\"]"));
        assertEquals("First Name Test6", entityTest.getFirstName());
        assertEquals("Last Name Test6", entityTest.getLastName());
        assertEquals("Address Test6", entityTest.getAddress());
        assertEquals("Email Test6", entityTest.getEmail());


    }

    @Test
    void findById() {
        //cria o mock de person
        Person entity = input.mockEntity();
        entity.setId(1L);
        //ao inves de chamar o repositorio, o mockito devolve o mock de person
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);
        //faz todos os testes no mock
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Email Test1", result.getEmail());
    }

    @Test
    void create() {
        //cria o mock antes e depois da persistencia
        Person entity = input.mockEntity();
        Person persisted = entity;
        persisted.setId(1L);
        //cria o mock do value object (que o create recebe no body param)
        PersonVO vo = input.mockVO();
        vo.setKey(1L);
        //ao inves de criar o entity, o mockito ja retorna o mock persistido
        when(repository.save(entity)).thenReturn(persisted);
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Email Test1", result.getEmail());
    }

    @Test
    void update() {
        Person entity = input.mockEntity();
        entity.setId(1L);
        Person persisted = entity;

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        PersonVO vo = input.mockVO();
        vo.setKey(1L);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Email Test1", result.getEmail());

    }

    @Test
    void delete() {
        Person entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }

    @Test
    void createNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
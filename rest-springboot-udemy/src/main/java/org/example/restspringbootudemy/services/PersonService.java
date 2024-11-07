package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.exceptions.ResourceNotFoundException;
import org.example.restspringbootudemy.entities.Person;
import org.example.restspringbootudemy.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {
        logger.info("Finding all persons");
        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding person by id " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
    }

    public Person createPerson(Person person) {
        logger.info("Creating person!");
        return repository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Updating person: " + person.getId());
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setEmail(person.getEmail());
        entity.setAddress(person.getAddress());
        return repository.save(entity);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting person: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }

}

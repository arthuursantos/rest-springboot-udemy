package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.entities.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

   private final AtomicLong counter = new AtomicLong();
   private Logger logger = Logger.getLogger(PersonService.class.getName());

    private Person mockPerson(int i) {
        return Person.builder()
                .id(counter.incrementAndGet())
                .firstName("FirstName " + i)
                .lastName("LastName " + i)
                .email("Email " + i)
                .address("Address " + i)
                .build();
    }

   public List<Person> findAll() {
       List<Person> persons = new ArrayList<>();
       for (int i = 1; i <= 10; i++) {
           persons.add(mockPerson(i));
       }
       return persons;
   }

    public Person findById(String id) {
       logger.info("Finding person by id: " + id);
       return Person.builder()
               .id(counter.incrementAndGet())
               .firstName("Arthur")
               .lastName("Santos")
               .email("arthur.santos@gmail.com")
               .address("Rua Dom José, 774")
               .build();
   }

   public Person createPerson(Person person) {
        logger.info("Creating person!");
        return person;
   }

   public Person updatePerson(Person person) {
        logger.info("Updating person: " + person.getId());
        return person;
   }

   public void deletePerson(String id) {
        logger.info("Deleting person: " + id);
   }

}

package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.exceptions.ResourceNotFoundException;
import org.example.restspringbootudemy.data.vo.v1.PersonVO;
import org.example.restspringbootudemy.entities.Person;
import org.example.restspringbootudemy.mapper.DozerMapper;
import org.example.restspringbootudemy.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<PersonVO> findAll() {
        logger.info("Finding all persons");
        return DozerMapper.parseList(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding person by id " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO createPerson(PersonVO person) {
        logger.info("Creating person!");
        Person entity = DozerMapper.parseObject(person, Person.class);
        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVO updatePerson(PersonVO person) {
        logger.info("Updating person: " + person.getId());
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setEmail(person.getEmail());
        entity.setAddress(person.getAddress());
        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting person: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }

}

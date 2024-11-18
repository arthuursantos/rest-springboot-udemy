package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.PersonController;
import org.example.restspringbootudemy.controllers.exceptions.RequiredObjectIsNullException;
import org.example.restspringbootudemy.controllers.exceptions.ResourceNotFoundException;
import org.example.restspringbootudemy.data.vo.v1.PersonVO;
import org.example.restspringbootudemy.entities.Person;
import org.example.restspringbootudemy.mapper.DozerMapper;
import org.example.restspringbootudemy.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<PersonVO> findAll() {
        logger.info("Finding all persons");
        var persons = DozerMapper.parseList(repository.findAll(), PersonVO.class);
        persons
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO findById(Long id) {
        logger.info("Finding person by id " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("Creating person!");
        Person entity = DozerMapper.parseObject(person, Person.class);
        PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

//    public PersonVOv2 createPersonV2(PersonVOv2 person) {
//        logger.info("Creating person!");
//        Person entity = mapper.convertPersonVoToPerson(person);
//        return mapper.convertPersonToPersonVO(repository.save(entity));
//    }

    public PersonVO update(PersonVO person) {
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("Updating person: " + person.getKey());
        Person entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setEmail(person.getEmail());
        entity.setAddress(person.getAddress());
        PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting person: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }

}

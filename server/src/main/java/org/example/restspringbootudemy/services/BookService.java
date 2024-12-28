package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.BookController;
import org.example.restspringbootudemy.controllers.PersonController;
import org.example.restspringbootudemy.dto.PersonVO;
import org.example.restspringbootudemy.exceptions.RequiredObjectIsNullException;
import org.example.restspringbootudemy.exceptions.ResourceNotFoundException;
import org.example.restspringbootudemy.dto.BookVO;
import org.example.restspringbootudemy.entities.Book;
import org.example.restspringbootudemy.services.mapper.DozerMapper;
import org.example.restspringbootudemy.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private PagedResourcesAssembler<BookVO> assembler;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Finding all Book");
        var booksPage = repository.findAll(pageable);
        var booksPageVO = booksPage
                .map(p -> DozerMapper.parseObject(p, BookVO.class))
                .map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return assembler.toModel(
                booksPageVO,
                linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel());
    }

    public BookVO findById(Long id) {
        logger.info("Finding Book by ID " + id);
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        BookVO response = DozerMapper.parseObject(entity, BookVO.class);
        response.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return response;
    }

    public BookVO create(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();
        Book entity = DozerMapper.parseObject(book, Book.class);
        BookVO response = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        response.add(linkTo(methodOn(BookController.class).findById(response.getKey())).withSelfRel());
        return response;
    }

    public BookVO update(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();
        logger.info("Updating Book " + book.getKey());
        Book entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setLaunch_date(book.getLaunch_date());
        BookVO response = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        response.add(linkTo(methodOn(BookController.class).findById(response.getKey())).withSelfRel());
        return response;
    }

    public void delete(Long id) {
        logger.info("Deleting Book " + id);
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }

}

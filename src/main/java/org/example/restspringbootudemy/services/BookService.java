package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.BookController;
import org.example.restspringbootudemy.controllers.exceptions.RequiredObjectIsNullException;
import org.example.restspringbootudemy.controllers.exceptions.ResourceNotFoundException;
import org.example.restspringbootudemy.data.vo.v1.BookVO;
import org.example.restspringbootudemy.entities.Book;
import org.example.restspringbootudemy.mapper.DozerMapper;
import org.example.restspringbootudemy.repositories.BookRepository;
import org.springdoc.core.converters.ResponseSupportConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public List<BookVO> findAll() {
        logger.info("Finding all Book");
        var books = DozerMapper.parseList(repository.findAll(), BookVO.class);
        books.forEach(
                book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel())
        );
        return books;
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
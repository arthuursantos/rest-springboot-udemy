package org.example.restspringbootudemy.mocks;

import org.example.restspringbootudemy.dto.BookVO;
import org.example.restspringbootudemy.entities.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(1);
    }

    public BookVO mockVO() {
        return mockVO(1);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    private Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("AuthorTest"+number);
        book.setLaunch_date(LocalDate.of(2012, 12, 16));
        book.setTitle("TitleTest"+number);
        book.setPrice(10.0);
        return book;
    }

    private BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setKey(number.longValue());
        book.setAuthor("AuthorTest"+number);
        book.setLaunch_date(LocalDate.of(2012, 12, 16));
        book.setTitle("TitleTest"+number);
        book.setPrice(10.0);
        return book;
    }

}
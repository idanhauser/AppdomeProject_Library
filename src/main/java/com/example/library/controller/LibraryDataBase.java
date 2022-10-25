package com.example.library.controller;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LibraryDataBase implements ILibrary<Book> {



    private final BookRepository repository;

    //An BookRepository is injected by constructor into the controller.
    public LibraryDataBase(BookRepository repository) {
        this.repository = repository;

    }



    @Override
    public Book addBook(Book newBook) {
        return repository.save(newBook);

    }

    @Override
    public Book getBook(Long id) {
        Book book = repository.findById(id) //
                .orElseThrow(() -> new BookNotFoundException(id));
        return book;
    }


    @Override
    public void removeBook(Long id) {
        repository.deleteById(id);
    }
}

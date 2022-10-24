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

public class LibraryOperations implements ILibrary<Book> {

    private final BookRepository repository;
    private final BookModelAssembler assembler;

    //An BookRepository is injected by constructor into the controller.
    public LibraryOperations(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public CollectionModel<EntityModel<Book>> all() {
        List<EntityModel<Book>> employees = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @Override
    public Book newBook(Book newBook) {
        return repository.save(newBook);

    }

    @Override
    public EntityModel<Book> one(Long id) {
        Book book = repository.findById(id) //
                .orElseThrow(() -> new BookNotFoundException(id));

        return assembler.toModel(book);
    }

    @Override
    public Book replaceBook(Book newBook, Long id) {
        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    book.setNumberOfPages(newBook.getNumberOfPages());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    @Override
    public void deleteBook(Long id) {
        repository.deleteById(id);
    }
}

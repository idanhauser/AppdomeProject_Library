package com.example.library.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("api/v1/Library")
//indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RestController
public class BookController implements Library<Book> {
    private final BookRepository repository;
    private final BookModelAssembler assembler;

    //An BookRepository is injected by constructor into the controller.
    public BookController(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    //GET
    @GetMapping("/books")
    public
    //CollectionModel is another Spring HATEOAS container; it’s aimed at encapsulating collections of resources,and lets you include links.
    CollectionModel<EntityModel<Book>> all() {

        List<EntityModel<Book>> employees = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }


    // end::get-aggregate-root[]
    //POST
    @PostMapping("/books")
    public Book newBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }


    //GET
    // Single item
    @GetMapping("/books/{id}")
    public
    //EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    //@PathVariable annotation to extract the templated part of the URI, represented by the variable {id}
    EntityModel<Book> one(@PathVariable Long id) {

        Book book = repository.findById(id) //
                .orElseThrow(() -> new BookNotFoundException(id));

        return assembler.toModel(book);
    }

    //PUT
    @PutMapping("/books/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

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

    //DELETE
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


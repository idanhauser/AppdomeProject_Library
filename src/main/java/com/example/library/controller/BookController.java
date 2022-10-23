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
public class BookController {
    private final BookRepository repository;

    //An BookRepository is injected by constructor into the controller.
    BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/books")
    //GET
    //CollectionModel is another Spring HATEOAS container; it’s aimed at encapsulating collections of resources,and lets you include links.
    CollectionModel<EntityModel<Book>> all() {

        List<EntityModel<Book>> employees = repository.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(BookController.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(BookController.class).all()).withRel("books")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/books")
//POST
    Book newBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }

    // Single item
    @GetMapping("/books/{id}")
    //GET
    //EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    //@PathVariable annotation to extract the templated part of the URI, represented by the variable {id}
    EntityModel<Book> one(@PathVariable Long id) {

        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        //Asks that Spring HATEOAS build a link to the BookController's one() method, and flag it as a self link.
        return EntityModel.of(book, linkTo(methodOn(BookController.class).one(id)).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"));//Asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "books".
    }

    @PutMapping("/books/{id}")
//PUT
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

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

    @DeleteMapping("/books/{id}")
//DELETE
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


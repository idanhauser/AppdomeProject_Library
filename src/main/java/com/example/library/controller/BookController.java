package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/Library")
//indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RestController
public class BookController implements ILibrary<Book> {

    private final LibraryOperations Library;

    //An BookRepository is injected by constructor into the controller.
    public BookController(BookRepository repository, BookModelAssembler assembler) {
        Library = new LibraryOperations(repository, assembler);
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    //GET
    @GetMapping("/books")
    public
    //CollectionModel is another Spring HATEOAS container; it’s aimed at encapsulating collections of resources,and lets you include links.
    CollectionModel<EntityModel<Book>> all() {
        return Library.all();
    }


    // end::get-aggregate-root[]
    //POST
    @PostMapping("/books")
    public Book newBook(@RequestBody Book newBook) {
        return Library.newBook(newBook);
    }


    //GET
    // Single item
    @GetMapping("/books/{id}")
    public
    //EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    //@PathVariable annotation to extract the templated part of the URI, represented by the variable {id}
    EntityModel<Book> one(@PathVariable Long id) {
        return Library.one(id);

    }

    //PUT
    @PutMapping("/books/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

        return Library.replaceBook(newBook, id);
    }

    //DELETE
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        this.Library.deleteBook(id);
    }
}


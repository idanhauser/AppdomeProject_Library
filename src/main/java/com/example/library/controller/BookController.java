package com.example.library.controller;

import java.util.List;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

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
    List<Book> all() {
        return repository.findAll();
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
    Book one(@PathVariable Long id) {


        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/books/{id}")
//PUT
    Book replaceEmployee(@RequestBody Book newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    @DeleteMapping("/books/{id}")
//DELETE
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


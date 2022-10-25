package com.example.library.controller;

import cache.LruCache;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@RequestMapping("api/v1/Library")
//indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RestController
public class BookController {
    private static final int CAPACITY = 3;
    private static final int EXPIRE_TIME = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;


    private final LibraryDataBase libraryDB;

    private final BookModelAssembler assembler;
    private final LruCache<Long, Book> lruCache;

    //An BookRepository is injected by constructor into the controller.
    @Autowired
    public BookController(BookRepository repository, BookModelAssembler assembler) {
        this.assembler = assembler;

        libraryDB = new LibraryDataBase(repository);
        lruCache = new LruCache<>(CAPACITY, EXPIRE_TIME, TIME_UNIT);

    }


    //POST
    @PostMapping("/books")
    public Book addBook(@RequestBody Book newBook) {
        lruCache.put(newBook.getId(), newBook);//adding book to cache
        return libraryDB.addBook(newBook);//adding book to database
    }


    //GET
    // Single item
    @GetMapping("/books/{id}")
    public EntityModel<Book> getBook(@PathVariable Long id) {
        Book book = lruCache.get(id);
        if (book == null)//book is not in cache we have to read it from the database
        {
            book = libraryDB.getBook(id);
            lruCache.put(id, book);//adding it to the Cache.
        }
        return assembler.toModel(book);

    }

    //DELETE
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryDB.removeBook(id);//deleting book from database
        lruCache.delete(id);//deleting book from cache
    }
}


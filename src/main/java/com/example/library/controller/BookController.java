package com.example.library.controller;

import cache.LruCache;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
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
    public BookController(BookRepository repository, BookModelAssembler assembler) {
        this.assembler = assembler;
        libraryDB = new LibraryDataBase(repository, assembler);
        lruCache = new LruCache<>(CAPACITY, EXPIRE_TIME, TIME_UNIT);
    }


    //POST
    @PostMapping("/books")
    public Book newBook(@RequestBody Book newBook) {
        lruCache.put(newBook.getId(), newBook);//adding book to cache
        return libraryDB.newBook(newBook);//adding book to database
    }


    //GET
    // Single item
    @GetMapping("/books/{id}")
    public
    //EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    //@PathVariable annotation to extract the templated part of the URI, represented by the variable {id}
    EntityModel<Book> getBook(@PathVariable Long id) {
        Book book = lruCache.get(id);
        if (book == null)//book is not in cache we have to read it from the database
        {
            book = libraryDB.getBook(id);
            lruCache.put(id, book);//adding it to the Cache.
        }
        return assembler.toModel(book);

    }

  /*  //PUT
    @PutMapping("/books/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {
        Book editedBook;
        Book book = lruCache.get(id);
        if (book == null)//book is not in cache we have to read it from the database
        {
            try {
                book = LibraryDB.one(id);
                book.setName(newBook.getName());
                book.setAuthor(newBook.getAuthor());
                book.setNumberOfPages(newBook.getNumberOfPages());
            } catch (Exception ex) {
                if (ex.getClass() == BookNotFoundException.class) {
                    newBook.setId(id);
                } else {
                    throw ex;
                }
            }
            newBook(newBook);
        }
        //book was in cache:
        book.setName(newBook.getName());
        book.setAuthor(newBook.getAuthor());
        book.setNumberOfPages(newBook.getNumberOfPages());

    }*/

    //DELETE
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryDB.deleteBook(id);//deleting book from database
        lruCache.delete(id);//deleting book from cache
    }
}


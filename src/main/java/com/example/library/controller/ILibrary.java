package com.example.library.controller;

import com.example.library.model.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILibrary<T> {

    //T replaceBook(@RequestBody T addBook, @PathVariable Long id);

    Book addBook(@RequestBody Book newBook);

    Book getBook(@PathVariable Long id);



    void removeBook(@PathVariable Long id);


}

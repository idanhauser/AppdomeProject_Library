package com.example.library.controller;

import com.example.library.model.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface Library<T> {

    CollectionModel<EntityModel<T>> all();

    Book newBook(@RequestBody Book newBook);

    EntityModel<T> one(@PathVariable Long id);

    T replaceBook(@RequestBody T newBook, @PathVariable Long id);

    void deleteBook(@PathVariable Long id);


}

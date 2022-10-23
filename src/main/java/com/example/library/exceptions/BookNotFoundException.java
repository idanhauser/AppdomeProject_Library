package com.example.library.exceptions;

import sun.tools.java.Environment;

//BookNotFoundException is an exception used to indicate when an employee is looked up but not found.
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Could not find book " + id + System.lineSeparator());
    }
}

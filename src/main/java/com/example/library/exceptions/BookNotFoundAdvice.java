package com.example.library.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BookNotFoundAdvice {

    //signals that this advice is rendered straight into the response body.
    @ResponseBody
    //configures the advice to only respond if an EmployeeNotFoundException is thrown.
    @ExceptionHandler(BookNotFoundException.class)
    //says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //The body of the advice generates the content. In this case, it gives the message of the exception.
    String bookNotFoundHandler(BookNotFoundException ex) {
        return ex.getMessage();
    }
}


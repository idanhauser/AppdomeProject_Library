package com.example.library.controller;

import com.example.library.LibraryApplication;
import com.example.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTestIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddBook() {
        Book book = new Book("TestName", "Test", 23);


        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/Library/books", book, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetNotExistsBook() {
        ResponseEntity<String> responseEntity = null;
        responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/Library/books/", 2022, String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());

    }


}

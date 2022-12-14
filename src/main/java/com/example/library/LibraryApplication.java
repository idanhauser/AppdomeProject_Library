package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
is a meta-annotation that pulls in component scanning, autoconfiguration, and property support.
In essence, it will fire up a servlet container and serve up our service.
 */
@SpringBootApplication
public class LibraryApplication {

    // Please run this to start the server
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}

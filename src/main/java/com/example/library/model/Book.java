package com.example.library.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//is a JPA annotation to make this object ready for storage in a JPA-based data store.
@Entity
public class Book {
    private @Id
//is marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provider.
    @GeneratedValue
    long id;
    private String name;
    private String author;
    //  private Date publishDate;

    public Book() {
    }


    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        //this.publishDate = publishDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getId() == book.getId() && Objects.equals(getName(), book.getName()) && Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthor());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


}

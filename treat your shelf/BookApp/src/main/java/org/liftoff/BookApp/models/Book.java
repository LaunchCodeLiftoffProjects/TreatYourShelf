package org.liftoff.BookApp.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Book extends AbstractEntity {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String genre;

    public Book(){}

    public Book(String title,String author,String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }
}

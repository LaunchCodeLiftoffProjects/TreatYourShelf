package org.liftoff.BookApp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookFormDTO {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String author;

    @NotNull
    @NotBlank
    private String genre;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

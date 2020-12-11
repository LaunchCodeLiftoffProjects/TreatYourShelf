package org.liftoff.BookApp.models;

import javax.persistence.*;

@Entity
public class BookOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookOwner_seq")
    @SequenceGenerator(name = "bookOwner_seq", sequenceName = "bookOwner_seq", initialValue = 1, allocationSize = 1)
    private int id;

    private int bookId;
    private int userId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

}
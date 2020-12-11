package org.liftoff.BookApp.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @NotNull
    @Email
    private String communication;


    public User() {
    }

    public User(String username, String password, String communication) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.communication = communication;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getCommunication() {
        return communication;
    }

    public int getId() {
        return id;
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
package de.tuxfan.messengerbackend.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.UUID;

public class User {

    private String username;
    @JsonAlias("given_name")
    private String firstName;
    @JsonAlias("family_name")
    private String lastName;
    private String email;
    @JsonAlias("id")
    private UUID userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

package com.sleeptracker.sleeptracker.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    private String id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = true)
    private LocalDateTime emailVerified;

    @Column(nullable = true)
    private String image;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sleep> sleeps = new HashSet<>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getEmailVerified() {
        return this.emailVerified;
    }

    public void setEmailVerified(LocalDateTime emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Sleep> getSleeps() {
        return this.sleeps;
    }

    public void setSleeps(Set<Sleep> sleeps) {
        this.sleeps = sleeps;
    }

    public User() {
    }

}
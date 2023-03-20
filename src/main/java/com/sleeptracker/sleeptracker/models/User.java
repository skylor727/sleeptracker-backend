package com.sleeptracker.sleeptracker.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String image;

    @Column(unique = true, nullable = false)
    private String google_id;
    // Getters and setters, and constructors if needed
}

package com.bmt.Project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required") // Utilisation de @NotBlank pour inclure la vérification des espaces vides
    @Column(nullable = false) // Ajout d'une contrainte SQL pour s'assurer que la colonne ne peut pas être NULL
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false) // Contrainte pour rendre l'email unique et non NULL
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false) // Contrainte SQL pour s'assurer que la colonne ne peut pas être NULL
    private String password;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


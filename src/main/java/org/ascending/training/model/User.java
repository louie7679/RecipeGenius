package org.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    public User() {}

    public User(long id, String name, String email, String password, String dietaryRestrictions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dietary_restrictions")
    private String dietaryRestrictions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Recipe> recipes;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getDietaryRestrictions() { return dietaryRestrictions; }

    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public Set<Recipe> getRecipes() { return recipes; }

    public void setRecipes(Set<Recipe> recipes) { this.recipes = recipes; }
}
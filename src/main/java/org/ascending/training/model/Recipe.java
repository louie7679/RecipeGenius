package org.ascending.training.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {

    public Recipe() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private long id;

    @Column(name = "recipe_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "dietary_restrictions")
    private String dietaryRestrictions;

    //@Column(name = "usr_id")
    //private long userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "recipes", fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getDietaryRestrictions() { return dietaryRestrictions; }

    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Set<Ingredient> getIngredients() { return ingredients; }

    public void setIngredients(Set<Ingredient> ingredients) { this.ingredients = ingredients; }
}

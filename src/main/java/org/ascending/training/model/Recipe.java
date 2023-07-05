package org.ascending.training.model;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    public Recipe() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "recipe_name")
    private String recipeName;

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

    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public void setDescription(String description) { this.description = description; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public void setUserId() {  }
}

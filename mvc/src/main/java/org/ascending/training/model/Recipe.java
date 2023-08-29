package org.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipes")
public class Recipe {

    public Recipe() {}

    public Recipe(long id, String name, String description, String instructions, String dietaryRestrictions, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.dietaryRestrictions = dietaryRestrictions;
        this.user = user;
    }

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "recipe_ingredients",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id")}
    )
    @JsonIgnore
    private List<Ingredient> ingredients;

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

    public List<Ingredient> getIngredients() { return ingredients; }

    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    @Override
    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + (int)id;
//        result = prime * result + name.hashCode()
//        result = prime * result + description.hashCode();
//        result = prime * result + instructions.hashCode();
//        result = prime * result + dietaryRestrictions.hashCode();
//        return result;

        return Objects.hash(id, name, description, instructions, dietaryRestrictions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Recipe recipe = (Recipe) o;
        return id == recipe.id &&
                name.equals(recipe.name) &&
                Objects.equals(description, recipe.description) &&
                Objects.equals(instructions, recipe.instructions) &&
                Objects.equals(dietaryRestrictions, recipe.dietaryRestrictions);
    }


}

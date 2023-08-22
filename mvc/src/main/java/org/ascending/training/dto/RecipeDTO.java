package org.ascending.training.dto;

import org.ascending.training.model.Recipe;
import org.ascending.training.model.User;

import java.util.List;

public class RecipeDTO {
    private String name;
    private String description;
    private String instructions;
    private String dietaryRestrictions;
    private User user;
    private List<String> ingredients;

    public RecipeDTO(String name, String description, String instructions, String dietaryRestrictions, User user, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.dietaryRestrictions = dietaryRestrictions;
        this.user = user;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(this.name);
        recipe.setDescription(this.description);
        recipe.setInstructions(this.instructions);
        recipe.setDietaryRestrictions(this.dietaryRestrictions);
        recipe.setUser(this.user);
        return recipe;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

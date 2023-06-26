package org.ascending.training.model;

public class Recipe {

    public Recipe() {}

    private long recipeId;

    private String recipeName;

    private String description;

    private String instructions;

    private String dietaryRestrictions;

    private long userId;

    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public void setDescription(String description) { this.description = description; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public void setUserId(long userId) { this.userId = userId; }
}

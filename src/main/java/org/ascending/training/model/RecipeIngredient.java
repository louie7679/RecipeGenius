package org.ascending.training.model;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    public RecipeIngredient() {}

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "ingredient_id")
    private long ingredientId;

    public long getRecipeId() { return recipeId; }

    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public long getIngredientId() { return ingredientId; }

    public void setIngredientId(long ingredientId) { this.ingredientId = ingredientId; }
}

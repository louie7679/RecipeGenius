package org.ascending.training.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Recipe_Ingredients")
public class RecipeIngredient {

    public RecipeIngredient() {}

    private long recipeId;

    private long ingredientId;

    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public void setIngredientId(long ingredientId) { this.ingredientId = ingredientId; }
}

package org.ascending.training.repository;

import org.ascending.training.model.Recipe;
import org.ascending.training.model.RecipeIngredient;

import java.util.List;

public interface IRecipeIngredientDao {
    //Create
    void save(RecipeIngredient recipeIngredient);

    //Retrieve
    public List<RecipeIngredient> getRecipeIngredients();

    //Update = get + ... + save
    RecipeIngredient getById(Long recipeId, Long ingredientId);

    //Delete
    void delete(RecipeIngredient recipeIngredient);
}

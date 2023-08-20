package org.ascending.training.repository;

import org.ascending.training.model.Recipe;

import java.util.List;
import java.util.Set;

public interface IRecipeDao {
    //Create
    void save(Recipe recipe);

    //Retrieve
    public List<Recipe> getRecipes();

    Recipe getById(Long id);

    List<Recipe> getRecipesByIngredient(Long ingredientId);

    Set<Long> getIngredientIdsForRecipe(Long recipeId);

    Recipe getRecipeEagerBy(Long id);

    //Delete
    void delete(Recipe recipe);

    //Update = get + ... + save
    Recipe update(Recipe recipe);
}

package org.ascending.training.repository;

import org.ascending.training.model.RecipeIngredient;

import java.util.List;

public interface IRecipeIngredientDao {
    public List<RecipeIngredient> getRecipeIngredients();
}

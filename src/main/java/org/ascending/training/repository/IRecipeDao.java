package org.ascending.training.repository;

import org.ascending.training.model.Recipe;

import java.util.List;

public interface IRecipeDao {
    public List<Recipe> getRecipes();
}

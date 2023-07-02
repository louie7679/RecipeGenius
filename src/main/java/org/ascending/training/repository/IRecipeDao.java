package org.ascending.training.repository;

import org.ascending.training.model.Recipe;

import java.util.List;

public interface IRecipeDao {
    //Create
    void save(Recipe recipe);

    //Retrieve
    public List<Recipe> getRecipes();

    //Update = get + ... + save
    Recipe getById(Long id);

    //Delete
    void delete(Recipe recipe);
}

package org.ascending.training.service;

import org.ascending.training.model.Recipe;
import org.ascending.training.repository.IRecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private IRecipeDao recipeDao;

    public void save(Recipe recipe) {
        recipeDao.save(recipe);
    }

    public List<Recipe> getRecipes() {
        return recipeDao.getRecipes();
    }

    public Recipe getRecipeById(Recipe recipe) {
        return recipeDao.getById(recipe.getId());
    }

    public void delete(Recipe recipe) {
        recipeDao.delete(recipe);
    }

    public Recipe getRecipeEager(Long id) {
        return recipeDao.getRecipeEagerBy(id);
    }

    public Recipe getBy(Long id) {
        return recipeDao.getById(id);
    }

    public Recipe update(Recipe recipe) {
        return recipeDao.update(recipe);
    }
}

package org.ascending.training.service;

import org.ascending.training.model.Ingredient;
import org.ascending.training.model.Recipe;
import org.ascending.training.repository.IIngredientDao;
import org.ascending.training.repository.IRecipeDao;
import org.ascending.training.repository.exception.EmptyInputException;
import org.ascending.training.repository.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private IRecipeDao recipeDao;

    @Autowired
    private IIngredientDao ingredientDao;

    public void save(Recipe recipe) {
        recipeDao.save(recipe);
    }

    public void saveRecipeWithIngredients(Recipe recipe, List<String> ingredientNames) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredientName : ingredientNames) {
            Ingredient ingredient = ingredientDao.getByName(ingredientName);
            if (ingredient == null) {
                ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredientDao.save(ingredient);
            }
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);
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

    public List<Recipe> findRecipesByIngredients(List<String> userIngredients) {
        if (userIngredients == null) {
            throw new InvalidInputException("Ingredient list cannot be null.");
        }

        List<Ingredient> ingredients = new ArrayList<>();
        // Retrieve ingredient IDs for user-provided ingredients
        for (String ingredientName : userIngredients) {
            Ingredient ingredient = ingredientDao.getByName(ingredientName);
            if (ingredient != null) {
                ingredients.add(ingredient);
            }
        }

        // Retrieve recipes that use the provided ingredients
        List<Recipe> matchedRecipes = new ArrayList<>();
        for (Ingredient ingredient: ingredients) {
            List<Recipe> recipesUsingIngredients = recipeDao.getRecipesByIngredient(ingredient.getId());
            matchedRecipes.addAll(recipesUsingIngredients);
        }

        // Filter out recipes that require additional ingredients
        List<Recipe> finalRecipes = new ArrayList<>();
        for (Recipe recipe : matchedRecipes) {
            if (recipeContainsAllIngredients(recipe, ingredients)) {
                finalRecipes.add(recipe);
            }
        }
        return finalRecipes;
    }

    private boolean recipeContainsAllIngredients(Recipe recipe, List<Ingredient> ingredients) {
        Set<Long> requiredIngredientIds = recipeDao.getIngredientIdsForRecipe(recipe.getId());
        Set<Long> providedIngredientIds = ingredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toSet());

        // Check if the required ingredient IDs set contains all provided ingredient IDs
        return requiredIngredientIds.containsAll(providedIngredientIds);
    }
}

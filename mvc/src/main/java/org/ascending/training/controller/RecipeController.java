package org.ascending.training.controller;

import org.ascending.training.dto.IngredientDTO;
import org.ascending.training.dto.RecipeDTO;
import org.ascending.training.model.Recipe;
import org.ascending.training.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/recipe")
public class RecipeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Recipe recipe) {
        logger.info("Post a new object {}", recipe.getName()) ;
        recipeService.save(recipe);
    }

    @RequestMapping(value = "/createWithIngredients", method = RequestMethod.POST)
    public void createRecipeWithIngredients(@RequestBody RecipeDTO recipeDTO) {
        recipeService.saveRecipeWithIngredients(recipeDTO.getRecipe(), recipeDTO.getIngredients());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Recipe> getRecipes() {
        logger.info("This is recipe controller");
        return recipeService.getRecipes();
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public Recipe getRecipeById(@PathVariable(name = "Id") Long id) {
        logger.info("This is recipe controller, get by {}", id);
        return recipeService.getBy(id);
    }

//    @RequestMapping(value = "/matchByIngredients", method = RequestMethod.GET, params = {"ingredients"})
//    public Set<Recipe> findRecipesByIngredients(@RequestParam("ingredients") List<String> userIngredients) {
//        logger.info("Received request to match recipes by ingredients: {}", userIngredients);
//        Set<Recipe> matchedRecipes = recipeService.findRecipesByIngredients(userIngredients);
//        logger.info("Matching recipes completed. Matched recipes: {}", matchedRecipes);
//        return matchedRecipes;
//    }

    @RequestMapping(value = "/matchByIngredients", method = RequestMethod.GET)
    public List<Recipe> findRecipesByIngredients(@RequestBody IngredientDTO ingredientDTO) {
        List<String> userIngredients = ingredientDTO.getIngredients();
        logger.info("Received request to match recipes by ingredients: {}", userIngredients);
        List<Recipe> matchedRecipes = recipeService.findRecipesByIngredients(userIngredients);
        logger.info("Matching recipes completed. Matched recipes: {}", matchedRecipes);
        return matchedRecipes;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"name"})
    public Recipe updateRecipeName(@PathVariable("id") Long id, @RequestParam("name") String name) {
        logger.info("pass in variable id: {} and name {}", id.toString(), name);
        Recipe recipe = recipeService.getBy(id);
        recipe.setName(name);
        recipe = recipeService.update(recipe);
        return recipe;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"description"})
    public Recipe updateRecipeDescription(@PathVariable("id") Long id, @RequestParam("description") String description) {
        logger.info("pass in variable id: {} and description {}", id.toString(), description);
        Recipe recipe = recipeService.getBy(id);
        recipe.setDescription(description);
        recipe = recipeService.update(recipe);
        return recipe;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"instructions"})
    public Recipe updateRecipeInstructions(@PathVariable("id") Long id, @RequestParam("instructions") String instructions) {
        logger.info("pass in variable id: {} and instructions {}", id.toString(), instructions);
        Recipe recipe = recipeService.getBy(id);
        recipe.setInstructions(instructions);
        recipe = recipeService.update(recipe);
        return recipe;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"dietaryRestrictions"})
    public Recipe updateRecipeDietaryRestrictions(@PathVariable("id") Long id, @RequestParam("dietaryRestrictions") String dietaryRestrictions) {
        logger.info("pass in variable id: {} and dietary restrictions {}", id.toString(), dietaryRestrictions);
        Recipe recipe = recipeService.getBy(id);
        recipe.setDietaryRestrictions(dietaryRestrictions);
        recipe = recipeService.update(recipe);
        return recipe;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecipe(@PathVariable("id") Long id) {
        logger.info("Deleting recipe with id: {}", id);
        Recipe recipe = recipeService.getBy(id);
        recipeService.delete(recipe);
    }
}
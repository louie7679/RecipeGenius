package org.ascending.training.controller;

import org.ascending.training.model.Ingredient;
import org.ascending.training.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IngredientService ingredientService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Ingredient> getIngredients() {
        logger.info("This is ingredient controller");
        return ingredientService.getIngredients();
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public Ingredient getIngredientById(@PathVariable(name = "Id") Long id) {
        logger.info("This is ingredient controller, get by {}", id);
        return ingredientService.getBy(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"name"})
    public Ingredient updateIngredientName(@PathVariable("id") Long id, @RequestParam("name") String name) {
        logger.info("pass in variable id: {} and name {}", id.toString(), name);
        Ingredient ingredient = ingredientService.getBy(id);
        ingredient.setName(name);
        ingredient = ingredientService.update(ingredient);
        return ingredient;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"category"})
    public Ingredient updateIngredientCategory(@PathVariable("id") Long id, @RequestParam("category") String category) {
        logger.info("pass in variable id: {} and category {}", id.toString(), category);
        Ingredient ingredient = ingredientService.getBy(id);
        ingredient.setCategory(category);
        ingredient = ingredientService.update(ingredient);
        return ingredient;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Ingredient ingredient) {
        logger.info("Post a new object {}", ingredient.getName()) ;
        ingredientService.save(ingredient);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteIngredient(@PathVariable("id") Long id) {
        logger.info("Deleting ingredient with id: {}", id);
        Ingredient ingredient = ingredientService.getBy(id);
        ingredientService.delete(ingredient);
    }
}

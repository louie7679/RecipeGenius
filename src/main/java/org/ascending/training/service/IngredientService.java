package org.ascending.training.service;

import org.ascending.training.model.Ingredient;
import org.ascending.training.repository.IIngredientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IIngredientDao ingredientDao;

    public void save(Ingredient ingredient) {
        ingredientDao.save(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredientDao.getIngredients();
    }

    public Ingredient getIngredientById(Ingredient ingredient) {
        return ingredientDao.getById(ingredient.getId());
    }

    public void delete(Ingredient ingredient) {
        ingredientDao.delete(ingredient);
    }

    public Ingredient getIngredientEager(Long id) {
        return ingredientDao.getIngredientEagerBy(id);
    }

    public Ingredient getBy(Long id) {
        return ingredientDao.getById(id);
    }

    public Ingredient update(Ingredient ingredient) {
        return ingredientDao.update(ingredient);
    }
}

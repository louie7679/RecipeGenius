package org.ascending.training.repository;

import org.ascending.training.model.Ingredient;

import java.util.List;

public interface IIngredientDao {
    public List<Ingredient> getIngredients();
}

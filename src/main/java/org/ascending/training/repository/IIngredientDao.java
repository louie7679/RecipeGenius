package org.ascending.training.repository;

import org.ascending.training.model.Ingredient;

import java.util.List;

public interface IIngredientDao {
    //Create
    void save(Ingredient ingredient);

    //Retrieve
    public List<Ingredient> getIngredients();

    //Update = get + ... + save
    Ingredient getById(Long id);

    //Delete
    void delete(Ingredient ingredient);
}

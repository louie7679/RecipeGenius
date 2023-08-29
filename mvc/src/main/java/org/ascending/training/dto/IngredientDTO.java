package org.ascending.training.dto;

import java.util.List;

public class IngredientDTO {
    private List<String> ingredients;

    public IngredientDTO() {
        // Default constructor for Jackson
    }

    public IngredientDTO(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

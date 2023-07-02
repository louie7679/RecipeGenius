package org.ascending.training.model;

import javax.persistence.*;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    public Ingredient() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private long ingredientId;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Column(name = "category")
    private String category;

    public void setIngredientId(long ingredientId) { this.ingredientId = ingredientId; }

    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public void setCategory(String category) { this.category = category; }
}

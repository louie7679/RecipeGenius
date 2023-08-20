package org.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    public Ingredient() {}

    public Ingredient(long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private long id;

    @Column(name = "ingredient_name")
    private String name;

    @Column(name = "category")
    private String category;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Recipe> recipes;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public List<Recipe> getRecipes() { return recipes; }

    public void setRecipes(List<Recipe> recipes) { this.recipes = recipes; }
}

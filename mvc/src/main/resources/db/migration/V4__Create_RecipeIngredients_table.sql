CREATE TABLE Recipe_Ingredients (
    recipe_id INT,
    ingredient_id INT,
    FOREIGN KEY (recipe_id) REFERENCES Recipes(recipe_id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id),
    CONSTRAINT UC_Recipe_Ingredient UNIQUE (recipe_id, ingredient_id)
);
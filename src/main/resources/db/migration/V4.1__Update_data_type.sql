ALTER TABLE Recipes ALTER COLUMN user_id TYPE BIGINT;

ALTER TABLE Recipe_Ingredients
    ALTER COLUMN recipe_id TYPE BIGINT,
    ALTER COLUMN ingredient_id TYPE BIGINT;

-- Remove the unique constraint
ALTER TABLE Recipe_Ingredients
    DROP CONSTRAINT UC_Recipe_Ingredient;

-- Add the primary key constraint
ALTER TABLE Recipe_Ingredients
    ADD CONSTRAINT PK_Recipe_Ingredients PRIMARY KEY (recipe_id, ingredient_id);

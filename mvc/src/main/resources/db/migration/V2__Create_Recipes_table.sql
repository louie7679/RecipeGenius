CREATE TABLE Recipes (
    recipe_id BIGSERIAL PRIMARY KEY,
    recipe_name VARCHAR(30),
    description TEXT,
    instructions TEXT,
    dietary_restrictions VARCHAR(30),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
CREATE TABLE Users (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30),
    email VARCHAR(50),
    password VARCHAR(30),
    dietary_restrictions VARCHAR(30)
);
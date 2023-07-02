package org.ascending.training.repository;

import org.ascending.training.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RecipeJDBCDaoImpl implements IRecipeDao{
    static final String DB_URL = "jdbc:postgresql://localhost:5430/RecipeRecDB";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    @Override
    public void save(Recipe recipe) {

    }

    @Override
    public List<Recipe> getRecipes() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug("Start to getRecipes from Postgres via JDBC.");
        //Step1: Prepare the required data model
        List<Recipe> recipes = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Step2: Open a connection ->5 key points to connect db
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Step3: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Recipes";
            rs = stmt.executeQuery(sql);
            logger.info("Connects to DB successfully and execute the query.");

            //Step4: Extract data from result set
            while(rs.next()) {
                Long recipeId = rs.getLong("recipe_id");
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");
                String instructions = rs.getString("instructions");
                String dietaryRestrictions = rs.getString("dietaryRestrictions");
                Long userId = rs.getLong("user_id");

                Recipe recipe = new Recipe();
                recipe.setRecipeId(recipeId);
                recipe.setRecipeName(recipeName);
                recipe.setDescription(description);
                recipe.setInstructions(instructions);
                recipe.setDietaryRestrictions(dietaryRestrictions);
                recipe.setUserId(userId);
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            logger.error("Unable to connect to db or execute query", e);
            //e.printStackTrace();
        } finally {
            //Step6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Unable to close db connection", e);
                //e.printStackTrace();
            }
        }
        logger.info("Finish getRecipes {}", recipes);
        return recipes;
    }

    @Override
    public Recipe getById(Long id) {
        return null;
    }

    @Override
    public void delete(Recipe recipe) {

    }
}

package org.ascending.training.repository;

import org.ascending.training.model.RecipeIngredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientJDBCDaoImpl implements IRecipeIngredientDao{
    static final String DB_URL = "jdbc:postgresql://localhost:5430/RecipeRecDB";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    @Override
    public List<RecipeIngredient> getRecipeIngredients() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug("Start to getRecipeIngredients from Postgres via JDBC.");
        //Step1: Prepare the required data model
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Step2: Open a connection ->5 key points to connect db
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Step3: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Recipe_Ingredients";
            rs = stmt.executeQuery(sql);
            logger.info("Connects to DB successfully and execute the query.");

            //Step4: Extract data from result set
            while(rs.next()) {
                Long recipeId = rs.getLong("recipe_id");
                Long ingredientId = rs.getLong("ingredient_id");

                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipeId(recipeId);
                recipeIngredient.setIngredientId(ingredientId);
                recipeIngredients.add(recipeIngredient);
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
        logger.info("Finish getRecipeIngredients {}", recipeIngredients);
        return recipeIngredients;
    }
}

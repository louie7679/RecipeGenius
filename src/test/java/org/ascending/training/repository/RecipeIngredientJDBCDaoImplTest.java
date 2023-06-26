package org.ascending.training.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RecipeIngredientJDBCDaoImplTest {
    RecipeIngredientJDBCDaoImpl recipeIngredientDao;

    @Before
    public void setup() {
        recipeIngredientDao = new RecipeIngredientJDBCDaoImpl();
    }

    @After
    public void teardown() {
        recipeIngredientDao = null;
    }

    @Test
    public void getRecipeIngredientsTest() {
        assertEquals(0, recipeIngredientDao.getRecipeIngredients().size());
    }
}

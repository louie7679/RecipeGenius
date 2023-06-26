package org.ascending.training.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RecipeJDBCDaoImplTest {
    RecipeJDBCDaoImpl recipeDao;

    @Before
    public void setup() {
        recipeDao = new RecipeJDBCDaoImpl();
    }

    @After
    public void teardown() {
        recipeDao = null;
    }

    @Test
    public void getRecipesTest() {
        assertEquals(0, recipeDao.getRecipes().size());
    }
}

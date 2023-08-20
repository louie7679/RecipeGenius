package org.ascending.training.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class IngredientJDBCDaoImplTest {
    IngredientJDBCDaoImpl ingredientDao;

    @Before
    public void setup() {
        ingredientDao = new IngredientJDBCDaoImpl();
    }

    @After
    public void teardown() {
        ingredientDao = null;
    }

    @Test
    public void getIngredientsTest() {
        assertEquals(0, ingredientDao.getIngredients().size());
    }
}

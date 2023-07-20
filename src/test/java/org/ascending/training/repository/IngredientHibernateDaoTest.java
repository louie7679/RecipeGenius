package org.ascending.training.repository;

import org.ascending.training.model.Ingredient;
import org.ascending.training.model.User;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class IngredientHibernateDaoTest {
    @Mock
    private SessionFactory mockSessionFactory;

    @Mock
    private Session mockSession;

    @Mock
    private Query mockQuery;

    @Mock
    private Transaction mockTransaction;

    private IIngredientDao ingredientDao;

    @Before
    public void setUp() {
        initMocks(this);
        ingredientDao = new IngredientHibernateDaoImpl();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void saveTest_happyPath() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(ingredient)).thenReturn(ingredient.getId());
            doNothing().when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Call the method under test
            ingredientDao.save(ingredient);

            // Verify the expected interactions
            verify(mockSessionFactory).openSession();
            verify(mockSession).beginTransaction();
            // Assertion: Verify that save() was called with the expected User object
            verify(mockSession).save(ingredient);
            verify(mockTransaction).commit();
            verify(mockSession).close();
        }
    }

    @Test
    public void saveTest_getHibernateException() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(ingredient)).thenReturn(ingredient.getId());
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> ingredientDao.save(ingredient));

            verify(mockTransaction).rollback();
        }
    }

    @Test
    public void saveTest_nullTransaction() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            when(mockSession.save(ingredient)).thenReturn(ingredient.getId());
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> ingredientDao.save(ingredient));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getIngredientsTest_happyPath() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );
        List<Ingredient> result = List.of(ingredient);

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doNothing().when(mockSession).close();

            // Call the method under test
            List<Ingredient> actualResult = ingredientDao.getIngredients();

            // Assert the expected and actual results
            assertEquals(result, actualResult);
        }
    }

    @Test
    public void getIngredientsTest_getHibernateException() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );
        List<Ingredient> result = List.of(ingredient);

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);

            // Throw a HibernateException for the first call, and close session for the second call
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> ingredientDao.getIngredients());
        }
    }

    @Test
    public void getByIdTest_happyPath() {
        // Prepare test data
        Long ingredientId = 1L;
        Ingredient expectedIngredient = new Ingredient(
                ingredientId,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(Ingredient.class, ingredientId)).thenReturn(expectedIngredient);
            doNothing().when(mockSession).close();

            // Call the method under test
            Ingredient actualIngredient = ingredientDao.getById(ingredientId);

            // Verify the method was called with the correct parameters
            verify(mockSession).get(Ingredient.class, ingredientId);

            // Assert the expected and actual results
            assertEquals(expectedIngredient, actualIngredient);
        }
    }

    @Test
    public void getByIdTest_getHibernateException() {
        // Prepare test data
        Long ingredientId = 1L;
        Ingredient expectedIngredient = new Ingredient(
                ingredientId,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(Ingredient.class, ingredientId)).thenReturn(expectedIngredient);
            // Throw a HibernateException for the first call, and close session for the second call
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> ingredientDao.getById(ingredientId));
        }
    }

    @Test
    public void deleteTest_happyPath() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );

        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            doNothing().when(mockSession).delete(ingredient);
            doNothing().when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Call the method under test
            ingredientDao.delete(ingredient);

            // Verify the expected interactions
            verify(mockSessionFactory).openSession();
            verify(mockSession).beginTransaction();
            // Assertion: Verify that delete() was called with the expected Ingredient object
            verify(mockSession).delete(ingredient);
            verify(mockTransaction).commit();
            verify(mockSession).close();
        }
    }

    @Test
    public void deleteTest_getHibernateException(){
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            doNothing().when(mockSession).delete(ingredient);
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> ingredientDao.delete(ingredient));

            verify(mockTransaction).rollback();
        }
    }

    @Test
    public void deleteTest_nullTransaction() {
        // Prepare test data
        Ingredient ingredient = new Ingredient(
                1,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            doNothing().when(mockSession).delete(ingredient);
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> ingredientDao.delete(ingredient));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getIngredientEagerByTest_happyPath() {
        // Prepare test data
        Long ingredientId = 1L;
        Ingredient expectedIngredient = new Ingredient(
                ingredientId,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
            when(mockQuery.uniqueResult()).thenReturn(expectedIngredient);
            doNothing().when(mockSession).close();

            // Call the method under test and store the result
            Ingredient actualIngredient = ingredientDao.getIngredientEagerBy(ingredientId);
            // Assert the expected and actual results
            assertEquals(expectedIngredient, actualIngredient);
        }
    }

    @Test
    public void getIngredientEagerByTest_getHibernateException() {
        // Prepare test data
        Long ingredientId = 1L;
        Ingredient expectedIngredient = new Ingredient(
                ingredientId,
                "Flour",
                "Baking"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
            when(mockQuery.uniqueResult()).thenReturn(expectedIngredient);
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> ingredientDao.getIngredientEagerBy(ingredientId));
        }
    }
}

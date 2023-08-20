package org.ascending.training.repository;

import org.ascending.training.model.Recipe;
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
public class RecipeHibernateDaoTest {
    @Mock
    private SessionFactory mockSessionFactory;

    @Mock
    private Session mockSession;

    @Mock
    private Query mockQuery;

    @Mock
    private Transaction mockTransaction;

    private IRecipeDao recipeDao;

    @Before
    public void setUp() {
        initMocks(this);
        recipeDao = new RecipeHibernateDaoImpl();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void saveTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(recipe)).thenReturn(recipe.getId());
            doNothing().when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Call the method under test
            recipeDao.save(recipe);

            // Verify the expected interactions
            verify(mockSessionFactory).openSession();
            verify(mockSession).beginTransaction();
            // Assertion: Verify that save() was called with the expected Recipe object
            verify(mockSession).save(recipe);
            verify(mockTransaction).commit();
            verify(mockSession).close();
        }
    }

    @Test
    public void saveTest_getHibernateException() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(recipe)).thenReturn(recipe.getId());
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> recipeDao.save(recipe));

            verify(mockTransaction).rollback();
        }
    }

    @Test
    public void saveTest_nullTransaction() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            when(mockSession.save(recipe)).thenReturn(recipe.getId());
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> recipeDao.save(recipe));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getRecipesTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );
        List<Recipe> result = List.of(recipe);

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doNothing().when(mockSession).close();

            // Call the method under test
            List<Recipe> actualResult = recipeDao.getRecipes();

            // Assert the expected and actual results
            assertEquals(result, actualResult);
        }
    }

    @Test
    public void getRecipesTest_getHibernateException() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );
        List<Recipe> result = List.of(recipe);

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
            assertThrows(HibernateException.class, () -> recipeDao.getRecipes());
        }
    }

    @Test
    public void getByIdTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Long recipeId = 1L;
        Recipe expectedRecipe = new Recipe(
                recipeId,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(Recipe.class, recipeId)).thenReturn(expectedRecipe);
            doNothing().when(mockSession).close();

            // Call the method under test
            Recipe actualRecipe = recipeDao.getById(recipeId);

            // Verify the method was called with the correct parameters
            verify(mockSession).get(Recipe.class, recipeId);

            // Assert the expected and actual results
            assertEquals(expectedRecipe, actualRecipe);
        }
    }

    @Test
    public void getByIdTest_getHibernateException() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Long recipeId = 1L;
        Recipe expectedRecipe = new Recipe(
                recipeId,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(Recipe.class, recipeId)).thenReturn(expectedRecipe);
            // Throw a HibernateException for the first call, and close session for the second call
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> recipeDao.getById(recipeId));
        }
    }

    @Test
    public void deleteTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            doNothing().when(mockSession).delete(recipe);
            doNothing().when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Call the method under test
            recipeDao.delete(recipe);

            // Verify the expected interactions
            verify(mockSessionFactory).openSession();
            verify(mockSession).beginTransaction();
            // Assertion: Verify that delete() was called with the expected User object
            verify(mockSession).delete(recipe);
            verify(mockTransaction).commit();
            verify(mockSession).close();
        }
    }

    @Test
    public void deleteTest_getHibernateException(){
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            // when(mockSession.save(user)).thenReturn(null);
            doNothing().when(mockSession).delete(recipe);
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> recipeDao.delete(recipe));

            verify(mockTransaction).rollback();
        }
    }

    @Test
    public void deleteTest_nullTransaction() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Recipe recipe = new Recipe(
                1,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            doNothing().when(mockSession).delete(recipe);
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> recipeDao.delete(recipe));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getRecipeEagerByTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Long recipeId = 1L;
        Recipe expectedRecipe = new Recipe(
                recipeId,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
            when(mockQuery.uniqueResult()).thenReturn(expectedRecipe);
            doNothing().when(mockSession).close();

            // Call the method under test and store the result
            Recipe actualRecipe = recipeDao.getRecipeEagerBy(recipeId);
            // Assert the expected and actual results
            assertEquals(expectedRecipe, actualRecipe);
        }
    }

    @Test
    public void getRecipeEagerByTest_getHibernateException() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        Long recipeId = 1L;
        Recipe expectedRecipe = new Recipe(
                recipeId,
                "Chocolate Cake",
                "Decadent chocolate cake with a rich frosting.",
                "1. Prepare cake batter.\n2. Bake in the oven.\n3. Frost the cooled cake.",
                "None",
                user
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
            when(mockQuery.uniqueResult()).thenReturn(expectedRecipe);
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> recipeDao.getRecipeEagerBy(recipeId));
        }
    }
}

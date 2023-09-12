package org.ascending.training.repository;

import org.ascending.training.ApplicationBootstrap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

// @RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class UserHibernateDaoTest {
    @Autowired
    private SessionFactory mockSessionFactory;

    @Mock
    private Session mockSession;

    @Mock
    private Query mockQuery;

    @Mock
    private Transaction mockTransaction;

    @Autowired
    private IUserDao userDao;

    @Before
    public void setUp() {
        initMocks(this);
        // userDao = new UserHibernateDaoImpl();
    }

    @After
    public void tearDown() {

    }

//    @Test
//    public void saveTest_happyPath() {
//        // Prepare test data
//        User user = new User(
//                1,
//                "Emily",
//                "emily@example.com",
//                "password123",
//                "Vegan"
//        );
//        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
//            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
//            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
//
//            // Mock the behavior of the session factory and session
//            when(mockSessionFactory.openSession()).thenReturn(mockSession);
//            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//            when(mockSession.save(user)).thenReturn(user.getId());
//            // doNothing().when(mockSession).save(user);
//            doNothing().when(mockTransaction).commit();
//            doNothing().when(mockSession).close();
//
//            // Call the method under test
//            userDao.save(user);
//
//            // Verify the expected interactions
//            verify(mockSessionFactory).openSession();
//            verify(mockSession).beginTransaction();
//            // Assertion: Verify that save() was called with the expected User object
//            verify(mockSession).save(user);
//            verify(mockTransaction).commit();
//            verify(mockSession).close();
//        }
//    }

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

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(user)).thenReturn(user.getId());
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> userDao.save(user));

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

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            when(mockSession.save(user)).thenReturn(user.getId());
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> userDao.save(user));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getUsersTest_happyPath() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        List<User> result = List.of(user);

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doNothing().when(mockSession).close();

            // Call the method under test
            List<User> actualResult = userDao.getUsers();

            // Assert the expected and actual results
            assertEquals(result, actualResult);
        }
    }

    @Test
    public void getUsersTest_getHibernateException() {
        // Prepare test data
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        List<User> result = List.of(user);

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
            assertThrows(HibernateException.class, () -> userDao.getUsers());
        }
    }

    @Test
    public void getByIdTest_happyPath() {
        // Prepare test data
        Long userId = 1L;
        User expectedUser = new User(
                userId,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(User.class, userId)).thenReturn(expectedUser);
            doNothing().when(mockSession).close();

            // Call the method under test
            User actualUser = userDao.getById(userId);

            // Verify the method was called with the correct parameters
            verify(mockSession).get(User.class, userId);

            // Assert the expected and actual results
            assertEquals(expectedUser, actualUser);
        }
    }

    @Test
    public void getByIdTest_getHibernateException() {
        // Prepare test data
        Long userId = 1L;
        User expectedUser = new User(
                userId,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.get(User.class, userId)).thenReturn(expectedUser);
            // Throw a HibernateException for the first call, and close session for the second call
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> userDao.getById(userId));
        }
    }

//    @Test
//    public void deleteTest_happyPath() {
//        // Prepare test data
//        User user = new User(
//                1,
//                "Emily",
//                "emily@example.com",
//                "password123",
//                "Vegan"
//        );
//
//        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
//            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
//            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
//
//            // Mock the behavior of the session factory and session
//            when(mockSessionFactory.openSession()).thenReturn(mockSession);
//            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//            doNothing().when(mockSession).delete(user);
//            doNothing().when(mockTransaction).commit();
//            doNothing().when(mockSession).close();
//
//            // Call the method under test
//            userDao.delete(user);
//
//            // Verify the expected interactions
//            verify(mockSessionFactory).openSession();
//            verify(mockSession).beginTransaction();
//            // Assertion: Verify that delete() was called with the expected User object
//            verify(mockSession).delete(user);
//            verify(mockTransaction).commit();
//            verify(mockSession).close();
//        }
//    }

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

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            // when(mockSession.save(user)).thenReturn(null);
            doNothing().when(mockSession).delete(user);
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(HibernateException.class, () -> userDao.delete(user));

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

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            doNothing().when(mockSession).delete(user);
            doThrow(NullPointerException.class).when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Verify that a HibernateException is thrown when calling the method under test
            assertThrows(NullPointerException.class, () -> userDao.delete(user));

            // Verify the expected interactions
            verify(mockTransaction, never()).rollback();
        }
    }

    @Test
    public void getUserEagerByTest_happyPath() {
        // Prepare test data
        Long userId = 1L;
        User expectedUser = new User(
                userId,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );

        // Mock the static method HibernateUtil.getSessionFactory()
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            // Mock the behavior of the session factory and session
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
            when(mockQuery.uniqueResult()).thenReturn(expectedUser);
            doNothing().when(mockSession).close();

            // Call the method under test and store the result
            User actualUser = userDao.getUserEagerBy(userId);
            // Assert the expected and actual results
            assertEquals(expectedUser, actualUser);
        }
    }

//    @Test
//    public void getUserEagerByTest_getHibernateException() {
//        // Prepare test data
//        Long userId = 1L;
//        User expectedUser = new User(
//                userId,
//                "Emily",
//                "emily@example.com",
//                "password123",
//                "Vegan"
//        );
//
//        // Mock the static method HibernateUtil.getSessionFactory()
//        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
//            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
//
//            // Mock the behavior of the session factory and session
//            when(mockSessionFactory.openSession()).thenReturn(mockSession);
//            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
//            when(mockQuery.setParameter(any(String.class), any(Long.class))).thenReturn(mockQuery);
//            when(mockQuery.uniqueResult()).thenReturn(expectedUser);
//            doThrow(HibernateException.class).doNothing().when(mockSession).close();
//
//            // Verify that a HibernateException is thrown when calling the method under test
//            assertThrows(HibernateException.class, () -> userDao.getUserEagerBy(userId));
//        }
//    }
}

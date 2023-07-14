package org.ascending.training.repository;

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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class UserHibernateDaoImplTest {
    @Mock
    private SessionFactory mockSessionFactory;

    @Mock
    private Session mockSession;

    @Mock
    private Query mockQuery;

    @Mock
    private Transaction mockTransaction;

    private IUserDao userDao;

    @Before
    public void setUp() {
        initMocks(this);
        userDao = new UserHibernateDaoImpl();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void saveTest_happyPath() {
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            // Mock HibernateUtil.getSessionFactory() to return the mockSessionFactory
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(user)).thenReturn(null);
            doNothing().when(mockTransaction).commit();
            doNothing().when(mockSession).close();

            // Call the method under test
            userDao.save(user);

            // Verify the expected interactions
            verify(mockSessionFactory).openSession();
            verify(mockSession).beginTransaction();
            verify(mockSession).save(user); // Assertion: Verify that save() was called with the expected User object
            verify(mockTransaction).commit();
            verify(mockSession).close();
        }
    }

    @Test
    public void saveTest_HibernateException() {
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);
            when(mockSession.save(user)).thenReturn(null);
            doNothing().when(mockTransaction).commit();
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            assertThrows(HibernateException.class, () -> userDao.save(user));

            verify(mockTransaction).rollback();
        }
    }

    @Test
    public void saveTest_transactionNull() {
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(null);
            doNothing().when(mockSession).close();

            assertThrows(HibernateException.class, () -> userDao.save(user));

            verify(mockSession, never()).save(user);
            verify(mockTransaction, never()).commit();
            verify(mockTransaction, never()).rollback();
            verify(mockSession).close();
        }
    }

    @Test
    public void getUsersTest_happyPath() {
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        List<User> result = List.of(user);

        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doNothing().when(mockSession).close();

            List<User> actualResult = userDao.getUsers();
            assertEquals(result, actualResult);

        }
    }

    @Test
    public void getUsersTest_getHibernateException() {
        User user = new User(
                1,
                "Emily",
                "emily@example.com",
                "password123",
                "Vegan"
        );
        List<User> result = List.of(user);

        try(MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            assertThrows(HibernateException.class, () -> userDao.getUsers());
        }

    }
}

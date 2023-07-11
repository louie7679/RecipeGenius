package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
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

}

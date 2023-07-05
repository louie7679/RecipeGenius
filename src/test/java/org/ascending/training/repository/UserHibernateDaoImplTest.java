package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserHibernateDaoImplTest {
    private UserHibernateDaoImpl userHibernateDao;
    private User user1;

    @Before
    public void setUp() {
        userHibernateDao = new UserHibernateDaoImpl();
        user1 = new User();
        user1.setId(1);
        user1.setName("Bob");
        user1.setEmail("example@gmail.com");
        user1.setPassword("password");
        user1.setDietaryRestrictions("vegetarian");
        userHibernateDao.save(user1);
    }

    @After
    public void tearDown() {
        //userHibernateDao.delete(d1);
    }

    @Test
    public void getUsersTest() {
        assertEquals(1, userHibernateDao.getUsers().size());
        userHibernateDao.delete(user1);
    }

    @Test
    public void deleteUsersTest() {
        userHibernateDao.delete(user1);
        assertEquals(0, userHibernateDao.getUsers().size());
    }
}

package org.ascending.training.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserJDBCDaoImplTest {
    UserJDBCDaoImpl userDao;

    @Before
    public void setup() {
        userDao = new UserJDBCDaoImpl();
    }

    @After
    public void teardown() {
        userDao = null;
    }

    @Test
    public void getUsersTest() {
        assertEquals(0, userDao.getUsers().size());
    }
}

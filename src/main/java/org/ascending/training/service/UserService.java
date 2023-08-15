package org.ascending.training.service;

import org.ascending.training.model.User;
import org.ascending.training.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(User user) {
        return userDao.getById(user.getId());
    }

    public void delete(User user) {

        userDao.delete(user);
    }

    public User getUserEager(Long id) {
        return userDao.getUserEagerBy(id);
    }

    public User getBy(Long id) {
        return userDao.getById(id);
    }

    public User update(User user) {
        return userDao.update(user);
    }
}

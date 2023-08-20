package org.ascending.training.repository;

import org.ascending.training.model.User;

import java.util.List;

public interface IUserDao {
    //Create
    void save(User user);

    //Retrieve
    public List<User> getUsers();

    //Update
    User getById(Long id);

    User getUserEagerBy(Long id);

    //Delete
    void delete(User user);

    User update(User user);
}

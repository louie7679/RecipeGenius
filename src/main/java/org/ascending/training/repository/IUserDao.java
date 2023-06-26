package org.ascending.training.repository;

import org.ascending.training.model.User;

import java.util.List;

public interface IUserDao {
    public List<User> getUsers();

}

package org.ascending.training.repository;

import org.ascending.training.model.Role;

import java.util.List;

public interface IRoleDao {
    void save(Role role);

    public List<Role> getRoles();

    Role getById(Long id);

    Role update(Role role);

    void delete(Role role);
}

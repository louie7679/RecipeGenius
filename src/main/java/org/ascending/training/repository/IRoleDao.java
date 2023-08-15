package org.ascending.training.repository;

import org.ascending.training.model.Role;

import java.util.List;

public interface IRoleDao {
    public List<Role> getRoles();

    Role getById(Long id);

    Role update(Role role);
}

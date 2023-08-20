package org.ascending.training.repository;

import org.ascending.training.model.Role;
import org.ascending.training.model.SystemUser;

import java.util.List;

public interface ISystemUserDao {
    boolean save(SystemUser systemUser);

    boolean saveRole(SystemUser systemUser, Role role);

    SystemUser getSystemUserByEmail(String email);

    SystemUser getSystemUserById(Long id);

    SystemUser getSystemUserByCredentials(String email, String password) throws Exception;

    List<Role> getSystemUserRole(SystemUser systemUser);

    void delete(SystemUser systemUser);
}

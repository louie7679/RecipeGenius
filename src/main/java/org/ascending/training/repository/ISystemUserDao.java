package org.ascending.training.repository;

import org.ascending.training.model.SystemUser;

public interface ISystemUserDao {
    boolean save(SystemUser systemUser);

    SystemUser getSystemUserByEmail(String email);

    SystemUser getSystemUserById(Long id);

    SystemUser getSystemUserByCredentials(String email, String password) throws Exception;
}

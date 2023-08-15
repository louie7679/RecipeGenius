package org.ascending.training.service;

import org.ascending.training.model.Role;
import org.ascending.training.model.SystemUser;
import org.ascending.training.repository.ISystemUserDao;
import org.ascending.training.repository.SystemUserHibernateDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {
    @Autowired
    ISystemUserDao systemUserDao;

    // @Autowired
    // SystemUserHibernateDaoImpl systemUserDao;

    public void save(SystemUser systemUser) {
        systemUserDao.save(systemUser);
    }

    public void saveRole(SystemUser systemUser, Role role) {
        systemUserDao.saveRole(systemUser, role);
    }

    public SystemUser getSystemUserByCredentials(String email, String password) throws Exception {
        return systemUserDao.getSystemUserByCredentials(email, password);
    }

    public SystemUser getSystemUserByEmail(String email) {
        return systemUserDao.getSystemUserByEmail(email);
    }

    public SystemUser getSystemUserById(Long id) {
        return systemUserDao.getSystemUserById(id);
    }
}

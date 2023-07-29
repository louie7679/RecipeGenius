package org.ascending.training.service;

import org.ascending.training.model.SystemUser;
import org.ascending.training.repository.ISystemUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {
    @Autowired
    ISystemUserDao systemUserDao;

    public SystemUser getSystemUserByCredentials(String email, String password) throws Exception {
        return systemUserDao.getSystemUserByCredentials(email, password);
    }

    public SystemUser getSystemUserById(Long id) {
        return systemUserDao.getSystemUserById(id);
    }
}

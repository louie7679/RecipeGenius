package org.ascending.training.service;

import org.ascending.training.model.Role;
import org.ascending.training.repository.IRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    IRoleDao roleDao;

    public void save(Role role) {
        roleDao.save(role);
    }

    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    public Role update(Role role) {
        return roleDao.update(role);
    }

    public void delete(Role role) {
        roleDao.delete(role);
    }
}

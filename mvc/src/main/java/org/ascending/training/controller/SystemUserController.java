package org.ascending.training.controller;

import org.ascending.training.model.Role;
import org.ascending.training.model.SystemUser;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.RoleService;
import org.ascending.training.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class SystemUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> userSignUp(@RequestBody SystemUser systemUser) {
        // Create a new user by saving the provided SystemUser object
        systemUserService.save(systemUser);
        // Assign a default role to the newly created user
        Role role = roleService.getById(2L);
        logger.info("Default role: {}", role);
        systemUserService.saveRole(systemUser, role);
        // Sign-up process completed successfully
        return ResponseEntity.ok("Sign-up succeeded");
    }

    @RequestMapping(value = "/systemUser/{id}", method = RequestMethod.GET)
    public SystemUser getSystemUserById(@PathVariable(name = "id") Long id) {
        logger.info("Getting system user with id: {}", id);
        return systemUserService.getSystemUserById(id);
    }

    @RequestMapping(value = "/systemUser/{id}/roles", method = RequestMethod.GET)
    public List<Role> getSystemUserRole(@PathVariable(name = "id") Long id) {
        SystemUser systemUser = systemUserService.getSystemUserById(id);
        return systemUserService.getSystemUserRole(systemUser);
    }

    @RequestMapping(value = "/systemUser/{id}", method = RequestMethod.DELETE)
    public void deleteSystemUser(@PathVariable("id") Long id) {
        logger.info("Deleting system user with id: {}", id);
        SystemUser systemUser = systemUserService.getSystemUserById(id);
        systemUserService.delete(systemUser);
    }
}

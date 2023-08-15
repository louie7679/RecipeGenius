package org.ascending.training.controller;

import org.ascending.training.model.Role;
import org.ascending.training.model.SystemUser;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.RoleService;
import org.ascending.training.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemUserController {
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
        systemUserService.saveRole(systemUser, role);
        // Sign-up process completed successfully
        return ResponseEntity.ok("Sign-up succeeded");
    }
}

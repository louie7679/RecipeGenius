package org.ascending.training.controller;

import org.ascending.training.model.SystemUser;
import org.ascending.training.repository.exception.UserNotFoundException;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JWTService jwtService;

    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestBody SystemUser systemUser) throws Exception {
        try {
            SystemUser u = systemUserService.getSystemUserByCredentials(systemUser.getEmail(), systemUser.getPassword());
            logger.info(String.valueOf(u));
            if (u == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
            }
            return ResponseEntity.ok().body(jwtService.generateToken(u));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}

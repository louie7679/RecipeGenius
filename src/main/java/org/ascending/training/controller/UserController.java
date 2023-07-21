package org.ascending.training.controller;

import org.ascending.training.model.User;
import org.ascending.training.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getUsers() {
        logger.info("This is user controller");
        return userService.getUsers();
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(name = "Id") Long id) {
        logger.info("This is user controller, get by {}", id);
        return userService.getBy(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"dietaryRestrictions"})
    public User updateUserDietaryRestrictions(@PathVariable("id") Long id, @RequestParam("dietaryRestrictions") String dietaryRestrictions) {
        logger.info("pass in variable id: {} and dietary restrictions {}", id.toString(), dietaryRestrictions);
        User user = userService.getBy(id);
        user.setDietaryRestrictions(dietaryRestrictions);
        user = userService.update(user);
        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        logger.info("Post a new object {}", user.getName()) ;
        userService.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        logger.info("Deleting user with id: {}", id);
        User user = userService.getBy(id);
        userService.delete(user);
    }
}

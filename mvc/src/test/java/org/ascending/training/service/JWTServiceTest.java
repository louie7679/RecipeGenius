package org.ascending.training.service;

import io.jsonwebtoken.Claims;
import org.ascending.training.ApplicationBootstrap;
import org.ascending.training.model.SystemUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class JWTServiceTest {
    @Autowired
    private JWTService jwtService;

    @Test
    public void generateTokenTest() {
        SystemUser u = new SystemUser();
        u.setId(1);
        u.setName("Tengfei");
        String token = jwtService.generateToken(u);
        String[] array = token.split("\\.");
        boolean bool = array.length == 3;
        assertTrue(bool);
    }

    @Test
    public void decryptTokenTest() {
        SystemUser u = new SystemUser();
        u.setId(1);
        u.setName("Tengfei");
        String token = jwtService.generateToken(u);

        Claims claims = jwtService.decryptToken(token);
        String userName = claims.getSubject();

        assertEquals(u.getName(), userName);
    }
}

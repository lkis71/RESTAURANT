package com.restaurant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.service.UserService;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void testJoinUser() {

        // User user = User.createUser("1", "1", "1", "1", new Address("1", "1", "1"), "1", "1");
        // Long userId = userService.join(user);

        // assertTrue(userId != null);
        // assertEquals(1, userId);
    }
}

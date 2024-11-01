package com.sleeptracker.sleeptracker;

import com.sleeptracker.sleeptracker.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testGetName() {
        User user = new User("123", "TestUser", "testuser@example.com");

        String actualName = user.getName();

        assertEquals("TestUser", actualName, "The name should be TestUser");
    }
}

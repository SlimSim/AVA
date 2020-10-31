package com.slimsimapps.ava;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Getter;
import lombok.Setter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LombokIntegrationTest {

    @Test
    public void givenAnnotatedUser_thenHasGettersAndSetters() {
        User user = new User();
        user.setFirstName("Test");

        assertEquals(user.getFirstName(), "Test", "lombok does not work: https://www.baeldung.com/lombok-ide");
    }

    @Getter @Setter
    static class User {
        private String firstName;
    }
}
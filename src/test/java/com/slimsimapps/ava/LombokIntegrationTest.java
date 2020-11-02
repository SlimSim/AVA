package com.slimsimapps.ava;


import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Getter;
import lombok.Setter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LombokIntegrationTest {

    @Test
    public void givenAnnotatedUser_thenHasGettersAndSetters() {
        User user = new User();
        user.setFirstName("Test");

        assertEquals(user.getFirstName(), "Test", "lombok, getter/setter does not work: https://www.baeldung.com/lombok-ide");
    }

    @Test
    public void lombokEquals() {

        Square s1 = new Square(42, 42, "100");
        Square s2 = new Square(42, 42, "100");
        Square s3 = new Square(42, 42, "10 10");
        Square s4 = new Square(69, 420, "100");

        //assertEquals(s2, s1, "equals does not exist...");

        assertEquals(s1, s2, "lombok EqualsAndHashCode does not work: https://www.baeldung.com/lombok-ide");
        assertNotEquals(s1, s3, "lombok EqualsAndHashCode does not work: https://www.baeldung.com/lombok-ide");
        assertNotEquals(s2, s4, "lombok EqualsAndHashCode does not work: https://www.baeldung.com/lombok-ide");

    }

    @Getter @Setter
    static class User {
        private String firstName;
    }


    @AllArgsConstructor
    @EqualsAndHashCode
    static class Square {
        private final int width;
        private final int height;
        private final String name;

    }

}
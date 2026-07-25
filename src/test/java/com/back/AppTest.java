package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    @DisplayName("1 + 2 == 3")
    void t1() {
        App app = new App();
        int rs = app.plus(1, 2);

        assertEquals(3, rs);
    }

    @Test
    @DisplayName("10 + 20 == 30")
    void t2() {
        App app = new App();
        int rs = app.plus(10, 20);

        assertEquals(30, rs);
    }

    @Test
    @DisplayName("0 + 5 == 5")
    void t3() {
        App app = new App();
        int rs = app.plus(0, 5);

        assertEquals(5, rs);
    }
}

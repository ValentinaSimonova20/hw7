package org.example;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void checkSecond(){
        assertEquals(asList("3 1 5 2 4".split(" ")), homeWork.getLeaveOrder(5, 3));
    }



}
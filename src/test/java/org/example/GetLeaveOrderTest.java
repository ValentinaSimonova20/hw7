package org.example;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetLeaveOrderTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void checkSecond(){
        assertEquals(List.of(3, 1, 5, 2, 4), homeWork.getLeaveOrder(5, 3));
        assertEquals(List.of(1), homeWork.getLeaveOrder(1, 1));
        assertEquals(List.of(5, 1, 3, 4, 2), homeWork.getLeaveOrder(5, 5));
        assertEquals(List.of(2, 4, 6, 8, 10, 3, 7, 1, 9, 5), homeWork.getLeaveOrder(10, 2));
    }

}
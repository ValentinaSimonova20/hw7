package org.example;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OriginalHomeNumbersTest {

    HomeWork homeWork = new HomeWork();

    @ParameterizedTest
    @MethodSource("first")
    void checkFirst(TestCase testCase) {

        assertEquals(testCase.expected, homeWork.getOriginalDoorNumbers(testCase.maxDoors, testCase.actionList));
    }

    private List<TestCase> first() {
        return List.of(
                generateTestCase1(),
                generateTestCase2(),
                generateTestCase3(),
                generateTestCase4()
        );
    }


    private TestCase generateTestCase1() {
        TestCase testCase = new TestCase();
        testCase.parseExpected("5\n" +
                "4\n" +
                "6\n" +
                "4\n" +
                "7");
        testCase.parseInput("20 7\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5");
        return testCase;
    }

    private TestCase generateTestCase2() {
        TestCase testCase = new TestCase();
        testCase.parseExpected("5\n" +
                "4\n" +
                "6\n" +
                "4\n" +
                "7\n" +
                "12"
        );
        testCase.parseInput("20 8\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5\n" +
                "L 10"
        );
        return testCase;
    }

    private TestCase generateTestCase3() {
        TestCase testCase = new TestCase();
        testCase.parseExpected("2\n" +
                "4"
        );
        testCase.parseInput("4 4\n" +
                "D 1\n" +
                "D 2\n" +
                "L 1\n" +
                "L 2"
        );
        return testCase;
    }

    private TestCase generateTestCase4() {
        TestCase testCase = new TestCase();
        testCase.parseExpected("3\n" +
                "4"
        );
        testCase.parseInput("4 4\n" +
                "D 2\n" +
                "D 1\n" +
                "L 1\n" +
                "L 2"
        );
        return testCase;
    }


    @RequiredArgsConstructor
    static class TestCase {
        int maxDoors;
        List<Action> actionList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        public void parseInput(String input) {
            String[] lines = input.split("(\n|\r|\r\n)");
            maxDoors = Integer.valueOf(lines[0].split(" ")[0]);
            Arrays.stream(lines)
                    .skip(1)
                    .map(Action::parse)
                    .forEach(actionList::add);

        }


        public void parseExpected(String output) {
            String[] lines = output.split("(\n|\r|\r\n)");
            Arrays.stream(lines)
                    .map(Integer::parseInt)
                    .forEach(expected::add);
        }
    }
}

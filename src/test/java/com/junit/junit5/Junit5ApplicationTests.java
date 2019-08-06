package com.junit.junit5;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

public class Junit5ApplicationTests {

    private Calculator calculator;
    private boolean moreThanZero = false;
    private boolean lessThanZero = false;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        calculator = new Calculator();
        this.testInfo = testInfo;
        this.testReporter = testReporter;

    }

    @Nested
    @DisplayName("Sum methods together")
    class Sum {
        @Tag("Sum")
        @Test
        @DisplayName("Sum Positive Test")
        void sumTestPositive() {
            int expected = 10;
            int actualSum = calculator.getSum(4, 6);
            assertEquals(expected, actualSum, "add method is adding two numbers correctly");
        }

        @Test
        @DisplayName("Sum Negative Test")
        void sumTestNegative() {
            int expected = 10;
            int actualSum = calculator.getSum(7, 6);
            assertNotEquals(expected, actualSum);
        }

        @Test
        @DisplayName("IF Sum More Than Zero Test")
        void sumTestMoreThanZero() {
            int zero = 0;
            int actualSum = calculator.getSum(-2, 3);

            if (actualSum >= zero) {
                moreThanZero = true;
            }
            assertTrue("Positive number", moreThanZero);
        }

        @Test
        @DisplayName("If Sum Less Than Zero Test")
        void sumTestLessThanZero() {
            int zero = 0;
            int actualSum = calculator.getSum(-9, 3);

            if (actualSum <= zero) {
                lessThanZero = true;
            }
            assertTrue("Negative number", lessThanZero);
        }

    }


    @Test
    @EnabledOnOs(OS.LINUX)
    @DisplayName("Division to Zero Test")
    void divideTest() {

        assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0), "Can not be divided by zero");
    }

    @Test
    @DisplayName("Fail Test")
    @Disabled
    void testDisabled() {
        fail("This should fail and better to make disabled it");
    }

    @Test
    @DisplayName("booleans with assumptions")
    void sumLessThanZeroByAssumption() {
        String displayName = testInfo.getDisplayName();
        Optional<Class<?>> testClass = testInfo.getTestClass();
        Optional<Method> testMethod = testInfo.getTestMethod();
        testReporter.publishEntry("Running " + displayName + " with testClass " + testClass + " and with method " + testMethod);

        assumingThat(lessThanZero, () -> System.out.println("TRUE"));
    }

    @Test
    @DisplayName("booleans with assumptions")
    void sumMoreThanZeroByAssumption() {
        assumingThat(moreThanZero, () -> System.out.println("TRUE"));
    }

    @RepeatedTest(5)
    @DisplayName("Multiply")
    void multiply() {
        assertAll(
            () -> assertEquals(4, calculator.multiply(2, 2)),
            () -> assertEquals(9, calculator.multiply(3, 3)),
            () -> assertEquals(16, calculator.multiply(4, 4)),
            () -> assertEquals(25, calculator.multiply(5, 5))
        );
    }

}

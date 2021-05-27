package com.epam.multithreading.task1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class FactorialCalculatorTest {

    @Test
    void testFactorial() {

        BigInteger testValue = BigInteger.valueOf(25);

        FactorialCalculator sequentialFactorialCalculator = new SequentialFactorialCalculator();
        FactorialCalculator fjpFactorialCalculator = new FjpFactorialCalculator();
        BigInteger sequentialFactorial = sequentialFactorialCalculator.factorial(testValue);
        BigInteger fjpFactorial = fjpFactorialCalculator.factorial(testValue);
        log.info("Sequential factorial = " + sequentialFactorial);
        log.info("FJP factorial = " + fjpFactorial);
        assertEquals(sequentialFactorial, fjpFactorial);
    }

}
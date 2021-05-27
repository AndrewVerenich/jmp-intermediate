package com.epam.multithreading.task1;

import java.math.BigInteger;

public class SequentialFactorialCalculator implements FactorialCalculator {
    public BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Value should be positive");
        }
        if (n.compareTo(BigInteger.valueOf(2)) < 0) {
            return n;
        }
        return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }
}

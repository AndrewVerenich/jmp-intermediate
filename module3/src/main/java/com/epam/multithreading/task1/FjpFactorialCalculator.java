package com.epam.multithreading.task1;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FjpFactorialCalculator implements FactorialCalculator {
    public BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Value should be positive");
        }
        ForkJoinPool pool = ForkJoinPool.commonPool();
        BigInteger total = pool.invoke(new RecursiveFactorial(BigInteger.ONE, n));
        pool.shutdown();
        return total;
    }
}

class RecursiveFactorial extends RecursiveTask<BigInteger> {

    private BigInteger first, last;

    RecursiveFactorial(BigInteger first, BigInteger last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected BigInteger compute() {
        if (last.subtract(first).compareTo(BigInteger.ONE) == 0) {
            return first.multiply(last);
        } else if (last.subtract(first).compareTo(BigInteger.ZERO) == 0) {
            return first;
        } else {
            BigInteger middle = (last.add(first)).divide(BigInteger.valueOf(2));
            RecursiveFactorial left = new RecursiveFactorial(first, middle);
            RecursiveFactorial right = new RecursiveFactorial(middle.add(BigInteger.ONE), last);
            left.fork();
            return right.compute().multiply(left.join());
        }
    }
}

package com.epam.multithreading.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayMergeSorterTest {

    @Test
    public void testSorting() {
        int array[] = new int[500_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 5_000_000);
        }

        new ArrayMergeSorter().sort(array);


        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }

}
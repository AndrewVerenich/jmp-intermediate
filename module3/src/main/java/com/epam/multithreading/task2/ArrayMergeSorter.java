package com.epam.multithreading.task2;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ArrayMergeSorter {
    public void sort(int[] array) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new SortTask(array));
        pool.shutdown();
    }
}

class SortTask extends RecursiveAction {

    final int[] array;
    final int lo, hi;
    static final int THRESHOLD = 1000;

    SortTask(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    SortTask(int[] array) {
        this(array, 0, array.length);
    }

    protected void compute() {
        if (hi - lo < THRESHOLD)
            sortSequentially(lo, hi);
        else {
            int mid = (lo + hi) / 2;
            invokeAll(new SortTask(array, lo, mid),
                    new SortTask(array, mid, hi));
            merge(lo, mid, hi);
        }
    }

    void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    void merge(int lo, int mid, int hi) {
        int[] buf = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                    buf[i++] : array[k++];
    }
}

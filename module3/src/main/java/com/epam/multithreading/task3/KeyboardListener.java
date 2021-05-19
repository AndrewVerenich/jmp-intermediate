package com.epam.multithreading.task3;

import lombok.SneakyThrows;

import java.util.concurrent.RecursiveAction;

/**
 * Task which terminate process by pressing some reserved key 'c'.
 */
public class KeyboardListener extends RecursiveAction {

    public static char RESERVED_EXIT_KEY = 'c';

    @SneakyThrows
    @Override
    protected void compute() {
        while (true) {
            char c = (char) System.in.read();
            if (c == RESERVED_EXIT_KEY) {
                System.exit(0);
            }
        }
    }
}

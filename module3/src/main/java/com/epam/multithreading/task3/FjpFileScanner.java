package com.epam.multithreading.task3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;

@Slf4j
public class FjpFileScanner {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SearchTask searchDir = new SearchTask("/home/andver/Pictures");
        pool.execute(searchDir);
        pool.execute(new KeyboardListener());
        searchDir.join();

        log.info("Number of directories : " + SearchTask.DIRECTORY_COUNT);
        log.info("Number of files : " + SearchTask.FILE_COUNT);
        log.info("Total size : " + SearchTask.FILE_SIZE);
    }
}
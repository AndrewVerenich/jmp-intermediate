package com.epam.multithreading.task3;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class SearchTask extends RecursiveTask<List<String>> {

    private String rootDirectoryName;

    public static final AtomicInteger FILE_COUNT = new AtomicInteger(0);
    public static final AtomicInteger DIRECTORY_COUNT = new AtomicInteger(0);
    public static final AtomicLong FILE_SIZE = new AtomicLong(0);

    public SearchTask(String rootDirectoryName) {
        this.rootDirectoryName = rootDirectoryName;
    }

    @Override
    protected List<String> compute() {
        List<String> matchingFilesList = new ArrayList<>();
        List<SearchTask> taskList = new ArrayList<>();
        File directory = new File(rootDirectoryName);

        if (rootDirectoryName == null || "".equals(rootDirectoryName) || !directory.exists())
            throw new IllegalArgumentException("Directory name is not valid");

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                DIRECTORY_COUNT.incrementAndGet();
                SearchTask searchDirectory = new SearchTask(rootDirectoryName);
                searchDirectory.fork();
                taskList.add(searchDirectory);
            } else {
                FILE_COUNT.incrementAndGet();
                FILE_SIZE.addAndGet(file.length());
                matchingFilesList.add(file.getPath());
            }
            log.info("Searching....");
        }
        for (SearchTask sd : taskList) {
            List<String> intermediateResultList = sd.join();
            matchingFilesList.addAll(intermediateResultList);
        }
        return matchingFilesList;
    }

}
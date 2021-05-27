package com.epam.multithreading.task4;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Mock, which emulates Employee Rest client
 */
public class EmployeeRestClient {

    private List<Employee> employees = Arrays.asList(
            new Employee(1, "Andrei"),
            new Employee(2, "Alex"),
            new Employee(3, "Michael"),
            new Employee(4, "Olga"),
            new Employee(5, "Stacy"),
            new Employee(6, "Pavel"));

    public List<Employee> hiredEmployees() {
        return employees;
    }

    @SneakyThrows
    public int getSalary(int hiredEmployeeId) {
//      Simulates delay to catch difference in async and sync execution
        Thread.sleep(1000);
        return (1_000 + new Random().nextInt(hiredEmployeeId * 100));
    }
}

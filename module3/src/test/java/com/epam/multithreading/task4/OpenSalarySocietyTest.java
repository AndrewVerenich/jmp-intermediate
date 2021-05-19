package com.epam.multithreading.task4;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class OpenSalarySocietyTest {

    private final OpenSalarySociety salarySociety = new OpenSalarySociety();

    @Test
    public void testOpenSalarySociety() {

//      Async execution
        long startAsync = System.currentTimeMillis();
        List<Employee> employeesAsync = salarySociety.getEmployeesWithSalary(true);
        long finishAsync = System.currentTimeMillis();
        long timeAsync = finishAsync - startAsync;
        log.info("Time for execution async: " + timeAsync);

        employeesAsync.stream().map(Employee::getSalary).forEach(salary -> assertNotEquals(0, salary));

//      Sync execution
        long startSync = System.currentTimeMillis();
        List<Employee> employeesSync = salarySociety.getEmployeesWithSalary(false);
        long finishSync = System.currentTimeMillis();
        long timeSync = finishSync - startSync;
        log.info("Time for execution sync: " + timeSync);

        employeesSync.stream().map(Employee::getSalary).forEach(salary -> assertNotEquals(0, salary));

//      Assert that async execution time is significantly less than sync
        assertTrue((double) timeSync / timeAsync > 2);
    }
}
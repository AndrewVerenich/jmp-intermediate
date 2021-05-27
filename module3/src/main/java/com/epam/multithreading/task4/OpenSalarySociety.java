package com.epam.multithreading.task4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Slf4j
public class OpenSalarySociety {

    private final EmployeeRestClient employeeClient = new EmployeeRestClient();

    @SneakyThrows
    public List<Employee> getEmployeesWithSalary(boolean isAsyncExecution) {
        CompletableFuture<List<Employee>> employeeFuture = CompletableFuture.supplyAsync(employeeClient::hiredEmployees)
                .thenCompose(employees -> {

//                  Join another CompletionStage<List> that takes care of filling the getSalaryAsync of each hired employee
                    List<CompletionStage<Employee>> updatedEmployees = employees.stream()
                            .map(employee -> getSalary(isAsyncExecution, employee.getId()).thenApply(salary -> {
                                employee.setSalary(salary);
                                return employee;
                            }))
                            .collect(Collectors.toList());

                    CompletableFuture<Void> done = CompletableFuture
                            .allOf(updatedEmployees.toArray(new CompletableFuture[updatedEmployees.size()]));

                    return done.thenApply(v -> updatedEmployees.stream().map(CompletionStage::toCompletableFuture)
                            .map(CompletableFuture::join).collect(Collectors.toList()));
                })

//              End up with a List<CompletionStage> to get a final stage that completes upon completion of all these stages.
                .whenComplete((employees, throwable) -> {
                    if (throwable != null) {
                        throw new RuntimeException(throwable);
                    }
//                  Print hired Employees with their salaries on the final stage.
                    employees.forEach(employee -> log.info(String.valueOf(employee)));
                });

        return employeeFuture.toCompletableFuture().join();
    }

    private CompletionStage<Integer> getSalary(boolean isAsync, int employeeId) {
        if (isAsync) {
            return CompletableFuture.supplyAsync(() -> employeeClient.getSalary(employeeId)).exceptionally(th -> 0);
        }
        return CompletableFuture.completedFuture(employeeClient.getSalary(employeeId)).exceptionally(th -> 0);
    }
}
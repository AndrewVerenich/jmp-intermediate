package com.epam.multithreading.task4;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    int id;
    String name;
    int salary;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

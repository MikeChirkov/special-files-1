package ru.example.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.example.data.Employee;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class XMLServiceTest {
    final static String FILE_NAME = "data.xml";
    static List<Employee> employeeList;

    @BeforeAll
    public static void initVariable() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "John", "Smith", "USA", 25));
        employeeList.add(new Employee(2, "Inav", "Petrov", "RU", 23));
    }

    @Test
    void parseXML() {
        List<Employee> test = XMLService.parseXML(FILE_NAME);
        assertNotEquals(test.get(0).age, employeeList.get(1).age);
        assertEquals(test.get(0).lastName, employeeList.get(0).lastName);
    }
}
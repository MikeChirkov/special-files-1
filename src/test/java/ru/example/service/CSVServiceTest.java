package ru.example.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.example.data.Employee;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVServiceTest {
    final static String FILE_NAME = "data.csv";
    final static String[] COLUMNS = new String[]{"id", "firstName", "lastName", "country", "age"};
    static List<Employee> employeeList;

    @BeforeAll
    public static void initVariable() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "John", "Smith", "USA", 25));
        employeeList.add(new Employee(2, "Inav", "Petrov", "RU", 23));
    }

    @Test
    void parseCSV() {
        List<Employee> test = CSVService.parseCSV(FILE_NAME, COLUMNS);
        assertEquals(test.get(0).id, employeeList.get(0).id);
        assertEquals(test.get(0).country, employeeList.get(0).country);
    }

}
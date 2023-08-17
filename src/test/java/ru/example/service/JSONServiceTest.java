package ru.example.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.example.data.Employee;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JSONServiceTest {

    final static String FILE_NAME = "data.json";
    static List<Employee> employeeList;

    @BeforeAll
    public static void initVariable() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "John", "Smith", "USA", 25));
        employeeList.add(new Employee(2, "Inav", "Petrov", "RU", 23));
    }

    @Test
    void listToJson() {
        String json = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        String test = JSONService.listToJson(employeeList);
        assertEquals(json, test);
        assertNotEquals(null, test);
        assertNotEquals("", test);
    }

    @Test
    void jsonToList() {
        String json = FileService.readString(FILE_NAME);
        List<Employee> test = JSONService.jsonToList(json);
        assertEquals(test.get(1).age, employeeList.get(1).age);
        assertEquals(test.get(0).age, employeeList.get(0).age);
    }

    @Test
    void jsonToListHamcrest() {
        String json = FileService.readString(FILE_NAME);
        List<Employee> list = JSONService.jsonToList(json);
        assertThat(list, hasSize(2));
        assertThat(list, is(Matchers.not(Matchers.empty())));
    }

    @Test
    void listToJsonHamcrest() {
        String test = JSONService.listToJson(employeeList);
        assertThat(test, allOf(startsWith("["), endsWith("]")));
        assertThat(test, allOf(
                containsString("id"),
                containsString("firstName"),
                containsString("lastName"),
                containsString("country"),
                containsString("age")
        ));
    }
}
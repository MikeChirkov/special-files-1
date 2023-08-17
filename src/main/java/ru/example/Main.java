package ru.example;

import ru.example.data.Employee;
import ru.example.service.CSVService;
import ru.example.service.FileService;
import ru.example.service.JSONService;
import ru.example.service.XMLService;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columns = new String[]{"id", "firstName", "lastName", "country", "age"};
        List<Employee> employeeListCsv = CSVService.parseCSV("data.csv", columns);
        employeeListCsv.forEach(System.out::println);
        System.out.println();
        String json = JSONService.listToJson(employeeListCsv);
        FileService.writeToFile(json, "data.json");

        List<Employee> employeeListXml = XMLService.parseXML("data.xml");
        employeeListXml.forEach(System.out::println);
        System.out.println();
        String json2 = JSONService.listToJson(employeeListXml);
        FileService.writeToFile(json2, "data2.json");

        String jsonStr = FileService.readString("data2.json");
        List<Employee> employeeListJson = JSONService.jsonToList(jsonStr);
        employeeListJson.forEach(System.out::println);
    }
}
package ru.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employeeList = parseCSV();
        employeeList.forEach(System.out::println);
        String json = listToJson(employeeList);
        writeToFile(json);
    }

    private static List<Employee> parseCSV() {
        try (CSVReader csvReader = new CSVReader(new FileReader("data.csv"))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String listToJson(List<T> list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

    public static void writeToFile(String data) {
        try (FileWriter fw = new FileWriter("data.json")) {
            fw.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
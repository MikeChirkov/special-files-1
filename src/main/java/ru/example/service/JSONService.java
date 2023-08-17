package ru.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.example.data.Employee;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONService {
    public static <T> String listToJson(List<T> list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

    public static List<Employee> jsonToList(String jsonString) {
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<Employee>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}

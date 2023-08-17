package ru.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {
    public static void writeToFile(String data, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder(br.readLine());
            while (br.readLine() != null) {
                sb.append(br.readLine());
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

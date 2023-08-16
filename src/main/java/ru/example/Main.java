package ru.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employeeListCsv = parseCSV("data.csv");
        employeeListCsv.forEach(System.out::println);
        String json = listToJson(employeeListCsv);
        writeToFile(json, "data.json");

        List<Employee> employeeListXml = parseXML("data.xml");
        employeeListXml.forEach(System.out::println);
        String json2 = listToJson(employeeListXml);
        writeToFile(json2, "data2.json");
    }

    private static List<Employee> parseCSV(String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
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


    private static List<Employee> parseXML(String fileName) {
        try {
            List<Employee> resultList = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Employee employee = read(nodeList.item(i));
                if (employee != null)
                    resultList.add(employee);
            }
            return resultList;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Employee read(Node node) {
        Employee employee = null;
        NodeList nodeList = node.getChildNodes();
        if (nodeList.getLength() > 0)
            employee = new Employee();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                assert employee != null;
                switch (node_.getNodeName()) {
                    case "id":
                        employee.setId(Integer.parseInt(node_.getTextContent()));
                        break;
                    case "firstName":
                        employee.setFirstName(node_.getTextContent());
                        break;
                    case "lastName":
                        employee.setLastName(node_.getTextContent());
                        break;
                    case "country":
                        employee.setCountry(node_.getTextContent());
                        break;
                    case "age":
                        employee.setAge(Integer.parseInt(node_.getTextContent()));
                        break;
                    default:
                        break;
                }
            }
        }
        return employee;
    }

    public static <T> String listToJson(List<T> list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

    public static void writeToFile(String data, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
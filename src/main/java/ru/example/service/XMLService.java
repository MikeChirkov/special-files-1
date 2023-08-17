package ru.example.service;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.example.data.Employee;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLService {
    public static List<Employee> parseXML(String fileName) {
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
}

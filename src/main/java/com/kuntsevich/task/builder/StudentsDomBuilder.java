package com.kuntsevich.task.builder;

import com.kuntsevich.task.entity.Address;
import com.kuntsevich.task.entity.Student;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;

public class StudentsDomBuilder extends AbstractStudentsBuilder {
    private static final String STUDENT = "student";
    private static final String FACULTY = "faculty";
    private static final String NAME = "name";
    private static final String TELEPHONE = "telephone";
    private static final String ADDRESS = "address";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String LOGIN = "login";
    private static final int FIRST_ITEM = 0;
    private static final Logger log = Logger.getLogger(StudentsDomBuilder.class);
    private DocumentBuilder docBuilder;

    public StudentsDomBuilder() {
        studentsSet = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Parser configuration error", e);
        }
    }

    @Override
    public void buildSetStudents(String fileName) {
        Document doc;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList studentsList = root.getElementsByTagName(STUDENT);
            for (int i = 0; i < studentsList.getLength(); i++) {
                Element studentElement = (Element) studentsList.item(i);
                Student student = buildStudent(studentElement);
                studentsSet.add(student);
            }
        } catch (IOException e) {
            log.error("File error or I/O error", e);
        } catch (SAXException e) {
            log.error("Parsing failure", e);
        }
    }

    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        student.setFaculty(studentElement.getAttribute(FACULTY));
        student.setName(getElementTextContent(studentElement, NAME));
        int tel = Integer.parseInt(getElementTextContent(studentElement, TELEPHONE));
        student.setTelephone(tel);
        Address address = new Address();
        student.setAddress(address);
        Element addressElement = (Element) studentElement.getElementsByTagName(ADDRESS).item(FIRST_ITEM);
        address.setCountry(getElementTextContent(addressElement, COUNTRY));
        address.setCity(getElementTextContent(addressElement, CITY));
        address.setStreet(getElementTextContent(addressElement, STREET));
        student.setLogin(studentElement.getAttribute(LOGIN));
        return student;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(FIRST_ITEM);
        String text = node.getTextContent();
        return text;
    }
}

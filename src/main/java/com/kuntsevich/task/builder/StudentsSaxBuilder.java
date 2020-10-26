package com.kuntsevich.task.builder;

import com.kuntsevich.task.builder.handler.StudentHandler;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class StudentsSaxBuilder extends AbstractStudentsBuilder {
    private static final Logger log = Logger.getLogger(StudentsSaxBuilder.class);
    private final StudentHandler studentHandler;
    private XMLReader reader;

    public StudentsSaxBuilder() {
        studentHandler = new StudentHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(studentHandler);
        } catch (SAXException e) {
            log.error("SAX parser error", e);
        }
    }

    @Override
    public void buildSetStudents(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            log.error("SAX parser error", e);
        } catch (IOException e) {
            log.error("I/O error", e);
        }
        studentsSet = studentHandler.getStudents();
    }
}
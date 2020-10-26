package com.kuntsevich.task.builder.handler;

import com.kuntsevich.task.builder.StudentEnum;
import com.kuntsevich.task.entity.Address;
import com.kuntsevich.task.entity.Student;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class StudentHandler extends DefaultHandler {
    private static final String STUDENT = "student";
    private final Set<Student> students;
    private final EnumSet<StudentEnum> withText;
    private Student current = null;
    private StudentEnum currentEnum = null;

    public StudentHandler() {
        students = new HashSet<>();
        withText = EnumSet.range(StudentEnum.NAME, StudentEnum.STREET);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (STUDENT.equals(localName)) {
            current = new Student();
            Address address = new Address();
            current.setAddress(address);
            current.setLogin(attrs.getValue(0));
            if (attrs.getLength() == 2) {
                current.setFaculty(attrs.getValue(1));
            }
        } else {
            StudentEnum temp = StudentEnum.valueOf(localName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (STUDENT.equals(localName)) {
            students.add(current);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case NAME:
                    current.setName(s);
                    break;
                case TELEPHONE:
                    current.setTelephone(Integer.parseInt(s));
                    break;
                case STREET:
                    current.getAddress().setStreet(s);
                    break;
                case CITY:
                    current.getAddress().setCity(s);
                    break;
                case COUNTRY:
                    current.getAddress().setCountry(s);
                    break;
                default:
                    throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }
}
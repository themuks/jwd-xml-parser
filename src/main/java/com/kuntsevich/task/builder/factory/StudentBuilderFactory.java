package com.kuntsevich.task.builder.factory;

import com.kuntsevich.task.builder.AbstractStudentsBuilder;
import com.kuntsevich.task.builder.StudentsDomBuilder;
import com.kuntsevich.task.builder.StudentsSaxBuilder;
import com.kuntsevich.task.builder.StudentsStaxBuilder;

public class StudentBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    public AbstractStudentsBuilder createStudentBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new StudentsDomBuilder();
            case STAX:
                return new StudentsStaxBuilder();
            case SAX:
                return new StudentsSaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
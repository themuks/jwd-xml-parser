package com.kuntsevich.task.builder;

import com.kuntsevich.task.entity.Student;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStudentsBuilder {
    protected Set<Student> studentsSet;

    public AbstractStudentsBuilder() {
        studentsSet = new HashSet<Student>();
    }

    public abstract void buildSetStudents(String fileName);

    public Set<Student> getStudentsSet() {
        return studentsSet;
    }
}

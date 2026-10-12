package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private String className;
    private List<Student> students;

    public Classroom(String className) {
        this.className = className;
        this.students = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
        }
    }

    public double getAverage() {
        if (students.isEmpty()) return 0.0;
        double sum = 0;
        for (Student s : students) {
            sum += s.getGrade();
        }
        return sum / students.size();
    }

    public Student getHighestStudent() {
        if (students.isEmpty()) return null;
        Student highest = students.get(0);
        for (Student s : students) {
            if (s.getGrade() > highest.getGrade()) {
                highest = s;
            }
        }
        return highest;
    }

    public Student getLowestStudent() {
        if (students.isEmpty()) return null;
        Student lowest = students.get(0);
        for (Student s : students) {
            if (s.getGrade() < lowest.getGrade()) {
                lowest = s;
            }
        }
        return lowest;
    }

    @Override
    public String toString() {
        return className;
    }
}
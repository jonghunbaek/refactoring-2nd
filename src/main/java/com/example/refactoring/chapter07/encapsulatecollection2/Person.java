package com.example.refactoring.chapter07.encapsulatecollection2;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;
    private List<Course> courses;

    public Person(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        int idx = this.courses.indexOf(course);

        if (idx == -1) {
            throw new IllegalArgumentException();
        }

        this.courses.remove(idx);
    }
}

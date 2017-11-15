package com.venherak.domain;


import java.util.ArrayList;
import java.util.Collection;

public class Group extends AbstractEntity {

    private String name;
    private Collection<Student> students;
    private Collection<Lesson> lessons;

    public Group() {
        students = new ArrayList<>();
        lessons = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        students = new ArrayList<>();
        lessons = new ArrayList<>();
    }

    public Student getStudentById(long id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setLessons(Collection<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Collection<Lesson> getLessons() {
        return lessons;
    }

    public void addLessonCollection(Collection<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            addLesson(lesson);
        }
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.getGroups().add(this);
    }

    public void addStudentCollection(Collection<Student> students) {
        for (Student student : students) {
            this.addStudent(student);
        }
    }

    public void addStudent(Student student) {
        student.setGroup(this);
        this.students.add(student);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("\nGroup name: " + this.name + "\n");
        for (Student student : students) {
            string.append(student.toString()).append("\n");
        }
        return string.toString();
    }
}

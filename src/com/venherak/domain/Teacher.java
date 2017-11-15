package com.venherak.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Teacher extends AbstractEntity {
    private List<Lesson> lessons;
    private String firstName;
    private String lastName;

    public Teacher() {
        lessons = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        lessons = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean equals(Teacher o) {
        return firstName.equals(o.firstName) && lastName.equals(o.lastName);
    }

    public void AddLessonCollection(Collection<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            addLesson(lesson);
        }
    }

    public void addLesson(Lesson lesson) {
        lesson.setTeacher(this);
        this.lessons.add(lesson);
    }

    public Lesson getLessonById(long id) {
        for (Lesson lesson : lessons) {
            if (lesson.getId() == id) {
                return lesson;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getId() + " " + lastName + " " + firstName;
    }
}

package com.venherak.domain;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Lesson> lessonList;

    public University() {
        lessonList = new ArrayList<>();
    }

    public void addNewLesson(Lesson lesson) {
        this.lessonList.add(lesson);
    }

    public List<Lesson> getLessons() {
        return this.lessonList;
    }
}

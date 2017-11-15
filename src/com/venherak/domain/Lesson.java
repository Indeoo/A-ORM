package com.venherak.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.next;

public class Lesson extends AbstractEntity {
    private String title;
    private List<Group> groups;
    private Teacher teacher;
    private String room;
    private Time time;
    private Type type;

    public Lesson() {
        groups = new ArrayList<>();
    }

    public Lesson(String title, Teacher teacher, String room, Time time, Type type) {
        groups = new ArrayList<>();
        this.title = title;
        this.teacher = teacher;
        this.room = room;
        this.time = time;
        this.type = type;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setType(String type) {
        setType(Type.valueOf(type));
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        group.getLessons().add(this);
        this.groups.add(group);
    }

    public String getNextLessonTime() {
        LocalDate localDate = LocalDate.now();
        return localDate.with(next(this.time.getWeekday())).toString() +
                "\n Pair: " + this.time.getPairNumber();
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "Room: " + this.room + "\n";
    }
}

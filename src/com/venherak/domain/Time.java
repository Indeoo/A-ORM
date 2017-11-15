package com.venherak.domain;

import java.time.DayOfWeek;
import java.util.Date;

public class Time extends AbstractEntity {
    private DayOfWeek weekday;
    private Date date;

    private int pairNumber;

    public Time() {

    }

    public Time(DayOfWeek weekday, int pairNumber) {
        this.weekday = weekday;
        this.pairNumber = pairNumber;
    }

    public void setWeekday(String weekday) {
        setWeekday(DayOfWeek.valueOf(weekday));
    }

    public void setWeekday(DayOfWeek weekday) {

        this.weekday = weekday;
    }

    public DayOfWeek getWeekday() {

        return weekday;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public void setPairNumber(int pairNumber) {

        this.pairNumber = pairNumber;
    }

    public int getPairNumber() {

        return pairNumber;
    }

    @Override
    public String toString() {
        return "Weekday: " + weekday + "Pare number: " + pairNumber;
    }
}

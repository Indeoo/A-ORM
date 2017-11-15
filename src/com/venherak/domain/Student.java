package com.venherak.domain;

public class Student extends AbstractEntity {

    private String firstName;
    private String lastName;
    private int recordBookId;
    private Group group;

    public Student() {

    }

    public Student(String firstName, String lastName, int recordBookId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.recordBookId = recordBookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRecordBookId() {
        return recordBookId;
    }

    public void setRecordBookId(int recordBookId) {
        this.recordBookId = recordBookId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode() + recordBookId;
    }

    @Override
    public String toString() {
        return "Record Book ID:" + recordBookId + " Firstname: " + firstName + " LastName: " +
                "" + lastName + " Group: " + group.getName() + "\n";
    }
}
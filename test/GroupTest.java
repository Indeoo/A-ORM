package test;

import com.venherak.domain.*;
import org.junit.jupiter.api.Test;

import static com.venherak.domain.Type.LECTION;
import static java.time.DayOfWeek.FRIDAY;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupTest {

    @Test
    void addStudentTest1() {
        Group group = new Group("group-1");
        Student student = new Student("student_name", "student_surname", 1);
        group.addStudent(student);
        assertTrue(student.getGroup().equals(group));
        assertTrue(group.getStudents().contains(student));
    }

    @Test
    void addLessonTest1() {
        Group group = new Group("group-1");
        Lesson lesson = new Lesson("SP-2", new Teacher("Vitaliy", "Pavlov"), "303-18", new Time(FRIDAY, 1), LECTION);
        group.addLesson(lesson);
        assertTrue(lesson.getGroups().contains(group));
        assertTrue(group.getLessons().contains(lesson));
    }
}

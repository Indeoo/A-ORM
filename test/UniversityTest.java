package test;

import com.venherak.domain.Lesson;
import com.venherak.domain.Teacher;
import com.venherak.domain.Time;
import com.venherak.domain.University;
import org.junit.jupiter.api.Test;

import static com.venherak.domain.Type.LABWORK;
import static com.venherak.domain.Type.LECTION;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UniversityTest {

    @Test
    void universityTest1() {
        Lesson lesson = new Lesson("SP-2", new Teacher("Vitaliy", "Pavlov"), "303-18", new Time(MONDAY, 1), LABWORK);
        University university = new University();
        university.addNewLesson(lesson);
        assertEquals(lesson, university.getLessons().get(university.getLessons().size() - 1));
    }

    @Test
    void universityTest2() {
        Lesson lesson = new Lesson("MultiThreading",
                new Teacher("Oleksandr", "Korochkin"),
                "303-18",
                new Time(TUESDAY, 1),
                LECTION);
        University university = new University();
        university.addNewLesson(lesson);
        assertEquals(lesson, university.getLessons().get(university.getLessons().size() - 1));
    }
}

package test;

import com.venherak.domain.Lesson;
import com.venherak.domain.Teacher;
import com.venherak.domain.Time;
import org.junit.jupiter.api.Test;

import static com.venherak.domain.Type.LECTION;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.THURSDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonTest {

    @Test
    void getNextLessonTimeTest1() {
        Lesson lesson = new Lesson("SP-2", new Teacher("Vitaliy", "Pavlov"), "303-18", new Time(FRIDAY, 1), LECTION);
        assertEquals(lesson.getNextLessonTime(), "2017-11-10 Pair: 1");
    }

    @Test
    void getNextLessonTimeTest2() {
        Lesson lesson = new Lesson("Computer Architect",
                new Teacher("Volodymyr", "Steshyn"),
                "303-18",
                new Time(THURSDAY, 2),
                LECTION);assertEquals(lesson.getNextLessonTime(),
                "2017-11-09 Pair: 2");
    }
}

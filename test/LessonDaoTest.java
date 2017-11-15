package test;

import com.venherak.dao.DAOException;
import com.venherak.dao.LessonDAO;
import com.venherak.domain.Lesson;
import org.junit.jupiter.api.Test;

public class LessonDaoTest {
    @Test
    void LessonGetByIDTest() {
        LessonDAO lessonDAO = new LessonDAO();
        Lesson lesson;
        try {
            lesson = lessonDAO.getById(3);
            System.out.println(lesson);
            System.out.println(lesson.getTeacher());
            System.out.println(lesson.getGroups().get(2).getStudents());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}

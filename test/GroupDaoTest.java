package test;

import com.venherak.dao.DAOException;
import com.venherak.dao.GroupDAO;
import com.venherak.domain.Group;
import com.venherak.domain.Lesson;
import org.junit.jupiter.api.Test;

public class GroupDaoTest {

    @Test
    void getByIdTest() {
        GroupDAO groupDAO = new GroupDAO();
        Group group;
        try {
            group = groupDAO.getById(3);
            System.out.println(group);
            for(Lesson lesson: group.getLessons()) {
                System.out.println(lesson.getTeacher());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllTest() {

    }

    @Test
    void persistTest() {

    }

    @Test
    void deleteTest() {

    }
}

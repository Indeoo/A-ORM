package com.venherak.dao.mappings;

import com.venherak.dao.DAOException;
import com.venherak.dao.GroupDAO;
import com.venherak.dao.LessonDAO;
import com.venherak.domain.AbstractEntity;
import com.venherak.domain.Group;
import com.venherak.domain.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface LessonGroupMapping extends ManyToMany<Lesson, Group> {

    default String getObjectOneIdColumnName() {
        return "lesson_id";
    }

    default String getObjectTwoIdColumnName() {
        return "group_id";
    }

    default String getObjectOneId() {
        return "SELECT lesson_id FROM Lessons_Groups GROUP BY lesson_id";
    }

    default String getObjectTwoId() {
        return "SELECT group_id FROM Lessons_Groups GROUP BY group_id";
    }

    default String getIdMapping() {
        return "SELECT * FROM Lessons_Groups";
    }

    default void setterObjectTwo(AbstractEntity object, Group element) {
        ((Group) object).setLessons(element.getLessons());
    }

    @Override
    default void performAddMethod(Lesson lesson, Group group) {

        lesson.addGroup(group);
    }

    default void setterObjectOne(AbstractEntity object, Lesson element) {
        ((Lesson) object).setGroups(element.getGroups());

    }

    default void parseObjectOneResultSet(ResultSet resultSet, List<Lesson> lessons) throws DAOException {
        try {
            while (resultSet.next()) {
                LessonDAO lessonDAO = new LessonDAO();
                Lesson lesson = lessonDAO.getByIdLazy(resultSet.getLong(getObjectOneIdColumnName()));
                lessonDAO.ManyToOneTeacher(lesson);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void parseObjectTwoResultSet(ResultSet resultSet, List<Group> groups) throws DAOException {
        try {
            while (resultSet.next()) {
                GroupDAO groupDAO = new GroupDAO();
                Group group = groupDAO.getByIdLazy(resultSet.getLong(getObjectTwoIdColumnName()));
                groupDAO.getOneToManyMapping(group);
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

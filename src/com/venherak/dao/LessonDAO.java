package com.venherak.dao;

import com.venherak.dao.mappings.LessonGroupMapping;
import com.venherak.domain.Lesson;
import com.venherak.domain.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO extends AbstractDAO<Lesson> implements LessonGroupMapping {
    private static String TABLE_NAME = "Lessons";
    private static String ID_COLUMN_NAME = "lesson_id";

    @Override
    String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET title = ?, teacher_id = ?, room = ?, time_id = ?, type = ? " +
                "WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " (title,teacher_id,room,time_id,type) VALUES (?,?,?,?,?)";
    }

    @Override
    String getSelectByIdQuery() {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "= ?";
    }

    @Override
    String getAllQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + "WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    List<Lesson> parseResultGetAll(ResultSet resultSet) throws DAOException {
        List<Lesson> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getLong(ID_COLUMN_NAME));
                lesson.setType(resultSet.getString("type"));
                lesson.setRoom(resultSet.getString("room"));
                lesson.setTime(new TimeDAO().getById(resultSet.getLong("time_id")));
                lesson.setTeacher(new TeacherDAO().getByIdLazy(resultSet.getInt("teacher_id")));
                ManyToOneTeacher(lesson);
                getLessonsGroupsMapping(lesson);
                list.add(lesson);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing get all Lesson result", e);
        }
        return list;
    }

    @Override
    Lesson parseResultGetById(ResultSet resultSet) throws DAOException {
        Lesson lesson = new Lesson();
        try {
            resultSet.next();
            lesson.setId(resultSet.getLong(ID_COLUMN_NAME));
            lesson.setTitle(resultSet.getString("title"));
            lesson.setRoom(resultSet.getString("room"));
            lesson.setType(resultSet.getString("type"));
            lesson.setTime(new TimeDAO().getById(resultSet.getLong("time_id")));
            lesson.setTeacher(new TeacherDAO().getByIdLazy(resultSet.getInt("teacher_id")));
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing get Lesson by id result", e);
        }
        return lesson;
    }

    @Override
    void prepareStatementForInsert(PreparedStatement statement, Lesson object) throws DAOException {
        try {
            statement.setString(1, object.getTitle());
            statement.setLong(2, object.getTeacher().getId());
            statement.setString(3, object.getRoom());
            statement.setLong(4, object.getTime().getId());
            statement.setString(5, object.getType().toString());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing statement for insert Lesson");
        }
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement statement, Lesson object) throws DAOException {
        try {
            statement.setString(1, object.getTitle());
            statement.setLong(2, object.getTeacher().getId());
            statement.setString(3, object.getRoom());
            statement.setLong(4, object.getTime().getId());
            statement.setString(5, object.getType().toString());
            statement.setLong(6, object.getId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing statement for updating Lesson", e);
        }
    }

    public Lesson getById(long id) throws DAOException {
        Lesson lesson = getByIdLazy(id);
        ManyToOneTeacher(lesson);
        getLessonsGroupsMapping(lesson);
        return lesson;
    }

    public void ManyToOneTeacher(Lesson object) throws DAOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.getByIdLazy(object.getTeacher().getId());
        teacherDAO.getOneToManyMapping(teacher);
        teacher.getLessons().remove(teacher.getLessonById(object.getId()));
        teacher.addLesson(object);
    }
}

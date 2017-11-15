package com.venherak.dao;

import com.venherak.dao.mappings.OneToMany;
import com.venherak.domain.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends AbstractDAO<Teacher> implements OneToMany<Teacher> {
    private static String TABLE_NAME = "Teachers";
    private static String ID_COLUMN_NAME = "teacher_id";


    @Override
    String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " (firstname,lastname) VALUES (?,?)";
    }

    @Override
    String getSelectByIdQuery() {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }
    @Override
    String getAllQuery() {
        return "SELECT * FROM " + TABLE_NAME + " LEFT JOIN Lessons USING(" + ID_COLUMN_NAME + ")";
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    public String getOneToManySQLQuery() {
        return "SELECT * FROM " + TABLE_NAME + "" +
                " JOIN Lessons USING(" + ID_COLUMN_NAME + ") WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    List<Teacher> parseResultGetAll(ResultSet resultSet) throws DAOException {
        List<Teacher> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong(ID_COLUMN_NAME));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                LessonDAO lessonDAO = new LessonDAO();
                for (int i = 0; i < teacher.getLessons().size(); i++) {
                    teacher.getLessons().set(i, lessonDAO.getById(teacher.getLessons().get(i).getId()));
                }
                list.add(teacher);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing getAll Teacher result", e);
        }
        return list;
    }

    @Override
    Teacher parseResultGetById(ResultSet resultSet) throws DAOException {
        Teacher teacher = new Teacher();
        try {
            resultSet.next();
            teacher.setId(resultSet.getLong(ID_COLUMN_NAME));
            teacher.setFirstName(resultSet.getString("firstname"));
            teacher.setLastName(resultSet.getString("lastname"));
            for (int i = 0; i < teacher.getLessons().size(); i++) {
                teacher.getLessons().set(i, new LessonDAO().getById(teacher.getLessons().get(i).getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public void parseOneToMany(ResultSet resultSet, Teacher object) throws DAOException {
        try {
            while (resultSet.next()) {
                object.addLesson(new LessonDAO().getByIdLazy(resultSet.getLong("lesson_id")));
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    public Teacher getById(long id) throws DAOException {
        Teacher teacher = getByIdLazy(id);
        getOneToManyMapping(teacher);
        LessonDAO lessonDAO = new LessonDAO();
        for (int i = 0; i < teacher.getLessons().size(); i++) {
            teacher.getLessons().set(i, lessonDAO.getById(teacher.getLessons().get(i).getId()));
        }
        return teacher;
    }

    @Override
    void prepareStatementForInsert(PreparedStatement statement, Teacher object) throws DAOException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing statement for inserting Teacher");
        }
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement statement, Teacher object) throws DAOException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setLong(3, object.getId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing statement for updating Teacher", e);
        }
    }
}

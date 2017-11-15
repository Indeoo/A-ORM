package com.venherak.dao;

import com.venherak.domain.Group;
import com.venherak.domain.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student> {
    private static String TABLE_NAME = "Students";
    private static String ID_COLUMN_NAME = "student_id";

    @Override
    String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET group_id = ?, firstname = ?, lastname = ?, record_book_id = ? " +
                "WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " (group_id,firstname,lastname,record_book_id) VALUES (?,?,?,?)";
    }

    @Override
    String getAllQuery() {
        return "SELECT * FROM" + TABLE_NAME;
    }

    @Override
    String getSelectByIdQuery() {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    List<Student> parseResultGetAll(ResultSet resultSet) throws DAOException {
        return null;
    }

    @Override
    Student parseResultGetById(ResultSet resultSet) throws DAOException {
        Student student = new Student();
        try {
            resultSet.next();
            student.setId(resultSet.getLong(ID_COLUMN_NAME));
            student.setFirstName(resultSet.getString("firstname"));
            student.setLastName(resultSet.getString("lastname"));
            student.setRecordBookId(resultSet.getInt("record_book_id"));
            student.setGroup(new GroupDAO().getByIdLazy(resultSet.getInt("group_id")));
            student.getGroup().getStudents().add(student);
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing get Student by id result", e);
        }
        return student;
    }

    @Override
    void prepareStatementForInsert(PreparedStatement statement, Student object) throws DAOException {
        try {
            statement.setLong(1, object.getGroup().getId());
            statement.setString(2, object.getFirstName());
            statement.setString(3, object.getLastName());
            statement.setInt(4, object.getRecordBookId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Student statement for insert");
        }
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement statement, Student object) throws DAOException {
        try {
            statement.setLong(1, object.getGroup().getId());
            statement.setString(2, object.getFirstName());
            statement.setString(3, object.getLastName());
            statement.setInt(4, object.getRecordBookId());
            statement.setLong(5, object.getId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Student statement for update", e);
        }
    }

    @Override
    public List<Student> getAll() throws DAOException {
        List<Group> groups;
        try {
            groups = new GroupDAO().getAll();
        } catch (DAOException e) {
            throw new DAOException("Exception while getting all Students from database", e);
        }
        List<Student> list = new ArrayList<>();
        for (Group group : groups) {
            list.addAll(group.getStudents());
        }
        return list;
    }

    @Override
    public Student getById(long id) throws DAOException {
        Student student = getByIdLazy(id);
        ManyToOneGroup(student);
        return student;
    }

    private void ManyToOneGroup(Student object) throws DAOException {
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.getByIdLazy(object.getGroup().getId());
        groupDAO.getOneToManyMapping(group);
        group.getStudents().remove(group.getStudentById(object.getId()));
        group.addStudent(object);
    }
}

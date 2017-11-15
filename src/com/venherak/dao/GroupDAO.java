package com.venherak.dao;

import com.venherak.dao.mappings.LessonGroupMapping;
import com.venherak.dao.mappings.OneToMany;
import com.venherak.domain.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO<Group> implements OneToMany<Group>, LessonGroupMapping {
    private static String TABLE_NAME = "Groups";
    private static String ID_COLUMN_NAME = "group_id";

    @Override
    String getUpdateQuery() {
        return "SELECT * FROM " + TABLE_NAME + " JOIN Students USING (" + ID_COLUMN_NAME + ") " +
                "WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " (group_name) VALUES (?)";
    }

    @Override
    String getSelectByIdQuery() {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getAllQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    public String getOneToManySQLQuery() {
        return "SELECT * FROM " + TABLE_NAME + " JOIN Students USING (" + ID_COLUMN_NAME + ") " +
                "WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    List<Group> parseResultGetAll(ResultSet resultSet) throws DAOException {
        List<Group> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getLong(ID_COLUMN_NAME));
                group.setName(resultSet.getString("group_name"));
                getOneToManyMapping(group);
                getLessonsGroupsMapping(group);
                list.add(group);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing Group get all result", e);
        }
        return list;
    }

    @Override
    Group parseResultGetById(ResultSet resultSet) throws DAOException {
        Group group = new Group();
        try {
            resultSet.next();
            group.setName(resultSet.getString("group_name"));
            group.setId(resultSet.getLong(ID_COLUMN_NAME));
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing Group get by id result", e);
        }
        return group;
    }

    void prepareStatementForInsert(PreparedStatement statement, Group object) throws DAOException {
        try {
            statement.setString(1, object.getName());

        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Group statement for insert");
        }
    }

    void prepareStatementForUpdate(PreparedStatement statement, Group object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setLong(2, object.getId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Group statement for update", e);
        }
    }

    @Override
    public Group getById(long id) throws DAOException {
        Group group = this.getByIdLazy(id);
        getOneToManyMapping(group);
        getLessonsGroupsMapping(group);
        return group;
    }

    @Override
    public void parseOneToMany(ResultSet resultSet, Group object) throws DAOException {
        try {
            while (resultSet.next()) {
                object.addStudent(new StudentDAO().getByIdLazy(resultSet.getLong("student_id")));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing One To Many in group", e);
        }
    }
}

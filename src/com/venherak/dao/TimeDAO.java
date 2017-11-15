package com.venherak.dao;

import com.venherak.domain.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO extends AbstractDAO<Time> {
    private static String TABLE_NAME = "Time";
    private static String ID_COLUMN_NAME = "time_id";

    @Override
    String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET weekday = ?, pairnumber = ? WHERE " + ID_COLUMN_NAME + " = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " (weekday, pairnumber) VALUES (?,?)";
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
    public Time getById(long id) throws DAOException {
        return super.getByIdLazy(id);
    }

    List<Time> parseResultGetAll(ResultSet resultSet) throws DAOException {
        List<Time> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Time time = new Time();
                time.setId(resultSet.getLong(ID_COLUMN_NAME));
                time.setPairNumber(resultSet.getInt("pairnumber"));
                time.setWeekday(resultSet.getString("weekday"));
                list.add(time);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing getAll Time result", e);
        }
        return list;
    }

    @Override
    Time parseResultGetById(ResultSet resultSet) throws DAOException {
        Time object = new Time();
        try {
            resultSet.next();
            object.setId(resultSet.getInt(ID_COLUMN_NAME));
            object.setPairNumber(resultSet.getInt("pairnumber"));
            object.setWeekday(resultSet.getString("weekday"));
        } catch (SQLException e) {
            throw new DAOException("Exception while parsing get Time by id result", e);
        }
        return object;
    }

    @Override
    void prepareStatementForInsert(PreparedStatement statement, Time object) throws DAOException {
        try {
            statement.setString(1, object.getWeekday().toString());
            statement.setInt(2, object.getPairNumber());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Time statement for insert");
        }
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement statement, Time object) throws DAOException {
        try {
            statement.setString(1, object.getWeekday().toString());
            statement.setInt(2, object.getPairNumber());
            statement.setLong(3, object.getId());
        } catch (SQLException e) {
            throw new DAOException("Exception while preparing Time statement for update");
        }
    }
}

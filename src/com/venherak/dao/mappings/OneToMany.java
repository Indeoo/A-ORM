package com.venherak.dao.mappings;

import com.venherak.dao.ConnectionUtil;
import com.venherak.dao.DAOException;
import com.venherak.domain.AbstractEntity;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public interface OneToMany<E extends AbstractEntity> {
    void parseOneToMany(ResultSet resultSet, E object) throws DAOException;

    String getOneToManySQLQuery();

    default void getOneToManyMapping(E object) throws DAOException {
        String querySQL = getOneToManySQLQuery();

        Connection connection = new ConnectionUtil().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(querySQL, RETURN_GENERATED_KEYS);
            statement.setLong(1, object.getId());
            resultSet = statement.executeQuery();
            this.parseOneToMany(resultSet, object);


        } catch (SQLException e) {
            throw new DAOException("Exception while mapping OneToMany", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

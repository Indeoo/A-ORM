package com.venherak.dao;

import com.venherak.domain.AbstractEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public abstract class AbstractDAO<E extends AbstractEntity> implements GenericDAO<E> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    private Connection getConnection() {
        return connectionUtil.getConnection();
    }

    abstract String getUpdateQuery();

    abstract String getCreateQuery();

    abstract String getAllQuery();

    abstract String getSelectByIdQuery();

    abstract String getDeleteQuery();

    abstract List<E> parseResultGetAll(ResultSet resultSet) throws DAOException;

    abstract E parseResultGetById(ResultSet resultSet) throws DAOException;

    abstract void prepareStatementForInsert(PreparedStatement statement, E object) throws DAOException;

    abstract void prepareStatementForUpdate(PreparedStatement statement, E object) throws DAOException;

    public void persist(E object) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            if (this.checkIfExists(object.getId())) {
                statement = connection.prepareStatement(getUpdateQuery(), RETURN_GENERATED_KEYS);
                prepareStatementForUpdate(statement, object);
            } else {
                statement = connection.prepareStatement(getCreateQuery(), RETURN_GENERATED_KEYS);
                prepareStatementForInsert(statement, object);
            }
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Exception while persisting object to database", e);
        } finally {
            try {
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

    @Override
    public boolean checkIfExists(long id) throws DAOException {
        String querySQL = getSelectByIdQuery();
        Connection connection = connectionUtil.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean result;
        try {
            statement = connection.prepareStatement(querySQL, RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Exception while checking on existing", e);
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
        return result;
    }

    public List<E> getAll() throws DAOException {
        String querySQL = getAllQuery();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<E> list;

        try {
            statement = connection.prepareStatement(querySQL);
            resultSet = statement.executeQuery(querySQL);
            list = parseResultGetAll(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Exception while getting all from database", e);
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
        return list;
    }

    public E getByIdLazy(long id) throws DAOException {
        String querySQL = getSelectByIdQuery();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        E object;

        try {
            statement = connection.prepareStatement(querySQL, RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            object = parseResultGetById(resultSet);

        } catch (SQLException | DAOException e) {
            throw new DAOException("Exception while getting element by ID from database", e);
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

        return object;
    }

    @Override
    public void delete(long id) throws DAOException {
        String querySQL = getDeleteQuery();
        Connection connection = connectionUtil.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(querySQL, RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Exception while executing delete query", e);
        } finally {
            try {
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

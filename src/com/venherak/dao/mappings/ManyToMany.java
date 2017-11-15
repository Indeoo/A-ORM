package com.venherak.dao.mappings;

import com.venherak.dao.ConnectionUtil;
import com.venherak.dao.DAOException;
import com.venherak.domain.AbstractEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public interface ManyToMany<objectOne extends AbstractEntity, objectTwo extends AbstractEntity> {

    String getObjectOneIdColumnName();

    String getObjectTwoIdColumnName();

    String getIdMapping();

    String getObjectOneId();

    String getObjectTwoId();

    void parseObjectOneResultSet(ResultSet resultSet, List<objectOne> object) throws DAOException;

    void parseObjectTwoResultSet(ResultSet resultSet, List<objectTwo> object) throws DAOException;

    void setterObjectOne(AbstractEntity object, objectOne element);

    void setterObjectTwo(AbstractEntity object, objectTwo element);

    void performAddMethod(objectOne object_one, objectTwo object_two);

    default void getLessonsGroupsMapping(AbstractEntity object) throws DAOException {
        Connection connection = new ConnectionUtil().getConnection();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        List<objectOne> listObjectOne = new ArrayList<>();
        List<objectTwo> listObjectTwo = new ArrayList<>();

        try {
            statement = connection.prepareStatement(getObjectOneId(), RETURN_GENERATED_KEYS);
            resultSet = statement.executeQuery();
            parseObjectOneResultSet(resultSet, listObjectOne);

            statement = connection.prepareStatement(getObjectTwoId(), RETURN_GENERATED_KEYS);
            resultSet = statement.executeQuery();
            parseObjectTwoResultSet(resultSet, listObjectTwo);

            statement = connection.prepareStatement(getIdMapping(), RETURN_GENERATED_KEYS);
            resultSet = statement.executeQuery();
            int[][] matrix = new int[listObjectOne.size() + 1][listObjectTwo.size() + 1];
            getMappingMatrix(resultSet, matrix);
            formLinks(matrix, listObjectOne, listObjectTwo);
            getResult(listObjectOne, listObjectTwo, object);
        } catch (SQLException e) {
            throw new DAOException("", e);
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

    default void getResult(List<objectOne> listObjectOne, List<objectTwo> listObjectTwo, AbstractEntity object) {
        Class oneClass = object.getClass();
        if (oneClass.getSimpleName().equals(listObjectTwo.get(0).getClass().getSimpleName())) {
            for (objectTwo element : listObjectTwo) {
                if (element.getId() == object.getId()) {
                    setterObjectTwo(object, element);
                }
            }
        } else {
            for (objectOne element : listObjectOne) {
                if (element.getId() == object.getId()) {
                    setterObjectOne(object, element);
                }
            }
        }
    }

    default void getMappingMatrix(ResultSet resultSet, int[][] matrix) {
        try {
            while (resultSet.next()) {
                long objectOneId = resultSet.getLong(getObjectOneIdColumnName());
                long objectTwoId = resultSet.getLong(getObjectTwoIdColumnName());
                matrix[(int) (objectOneId - 1)][(int) objectTwoId - 1] = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void formLinks(int[][] matrix, List<objectOne> listObjectOne, List<objectTwo> listObjectTwo) {
        for (int i = 0; i < listObjectOne.size(); i++) {
            for (int j = 0; j < listObjectTwo.size(); j++) {
                if (matrix[i][j] == 1) {
                    performAddMethod(listObjectOne.get(i), listObjectTwo.get(j));
                }
            }
        }
    }
}
package com.venherak.dao;

import java.sql.*;

import static java.lang.Class.forName;

public class ConnectionUtil {
    private final static String DB_NAME = "foxminded";
    private final static String DB_PASSWORD = "";
    private final static String DB_USER = "root";
    private final static String DB_HOST = "localhost";
    private final static String DB_URL = "jdbc:mysql://" + DB_HOST + " :3306/" + DB_NAME
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public Connection getConnection() {
        Connection connection = null;
        try {
            forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
package com.mutantes.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String RDS_HOST = "mysqlmutantes.cbc3cfboq1vb.us-east-1.rds.amazonaws.com";
    private static final int RDS_PORT = 3306;
    private static final String RDS_DB_NAME = "RESULTMUTANTES";
    private static final String RDS_USERNAME = "admin";
    private static final String RDS_PASSWORD = "admin123";

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(getRdsUrl(), RDS_USERNAME, RDS_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Aquí puedes manejar los errores de cierre de conexión de acuerdo a tus necesidades
            } finally {
                connection = null;
            }
        }
    }

    private String getRdsUrl() {
        return String.format("jdbc:mysql://%s:%d/%s", RDS_HOST, RDS_PORT, RDS_DB_NAME);
    }
}

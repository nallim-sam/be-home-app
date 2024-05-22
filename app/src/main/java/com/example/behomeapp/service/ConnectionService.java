package com.example.behomeapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private static final String URL_XAMPP = "jdbc:mysql://127.0.0.1:3307/behome";
    private static final String URL_WORKBENCH = "jdbc:mysql://localhost:3306/behome";
    private static final String USER = "root";
    private static final String PASSWORD = "Camaleon98";
    private static final String PASSWORD_WORKBENCH = "Camaleon98";

    /**
     * Establece una conexión con la base de datos
     *
     * @return objeto Connection
     * @throws SQLException Si se produce un error al acceder a la BBDD
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_WORKBENCH, USER, PASSWORD_WORKBENCH);
    }
}

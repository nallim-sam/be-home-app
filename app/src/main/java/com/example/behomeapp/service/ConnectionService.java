package com.example.behomeapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private static final String URL_XAMPP = "jdbc:mysql://localhost:3307/behome";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * Establece una conexión con la base de datos
     *
     * @return objeto Connection
     * @throws SQLException Si se produce un error al acceder a la BBDD
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL_XAMPP, USER, PASSWORD);
    }
}

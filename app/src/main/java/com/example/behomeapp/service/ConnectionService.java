package com.example.behomeapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private static final String URL_XAMPP = "jdbc:mysql://10.0.2.2:3307/behome?useUnicode=true&characterEncoding=UTF-8";
    private static final String URL_XAMPP_MOVIL = "jdbc:mysql://192.168.1.204:3307/behome?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Establece una conexión con la base de datos
     *
     * @return objeto Connection
     * @throws SQLException Si se produce un error al acceder a la BBDD
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL_XAMPP, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el controlador MySQL JDBC", e);
        }

    }

}

package com.example.behomeapp.DBManager;

import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    private static final String NOMBRE_QUERY = "SELECT nombre " +
            "FROM usuario " +
            "WHERE email = ?";

    public static String obtenerNombre(String email) {

        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(NOMBRE_QUERY)) {

            preparedStatement.setString(1, email);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

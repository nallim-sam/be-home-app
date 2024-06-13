package com.example.behomeapp.DBManager;

import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioManager {

    private static final String USUARIOS_QUERY = "SELECT * " +
            "FROM usuario " +
            "WHERE email = ?";

    public static String obtenerNombre(String email) {

        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(USUARIOS_QUERY)) {

            preparedStatement.setString(1, email);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre");
                }
                return "Nombre no encontrado";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String obtenerUsuarioId(String email) {
        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(USUARIOS_QUERY)) {

            preparedStatement.setString(1, email);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("id");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

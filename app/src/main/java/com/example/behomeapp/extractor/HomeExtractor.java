package com.example.behomeapp.extractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeExtractor {

    private final static String QUERY_TAREAS = "SELECT description, date FROM tareas ORDER BY date DESC LIMIT 3";


    public void extraerActualizaciones(Connection connection) {


        try (final PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TAREAS) ;
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void extraerEventos(Connection connection) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TAREAS) ;
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

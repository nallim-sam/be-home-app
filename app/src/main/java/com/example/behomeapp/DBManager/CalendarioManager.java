package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.EventoModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarioManager {


    private static final String OBTENER_ID_CALENDARIO = "SELECT id " +
            "FROM calendario " +
            "WHERE id_piso = ?";
    private static final String OBTENER_EVENTOS_QUERY = "SELECT " +
            "e.id, " +
            "e.id_calendario, " +
            "e.nombre, " +
            "e.fecha " +
            "FROM Evento e " +
            "JOIN Calendario c " +
            "ON e.id_calendario = c.id " +
            "WHERE c.id_piso = ? " +
            "AND e.fecha = ?";

    private static final String INSERTAR_EVENTO_QUERY = "INSERT INTO evento " +
            "(id_calendario, nombre, fecha) VALUES (?, ?, ?)";

    public static int obtenerIdCalendario(String idPiso) {
        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_ID_CALENDARIO)) {
            preparedStatement.setString(1, idPiso);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Se ha producido un error al obtener el id del calendario", e);

        }
    }

    public static List<EventoModelo> obtenerEventosPorFecha(String idPiso, Date fecha) {

        final List<EventoModelo> eventos = new ArrayList<>();

        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_EVENTOS_QUERY)) {

            preparedStatement.setString(1, idPiso);
            preparedStatement.setDate(2, new java.sql.Date(fecha.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    EventoModelo evento = new EventoModelo();
                    evento.setId(resultSet.getInt("id"));
                    evento.setIdCalendario(resultSet.getInt("id_calendario"));
                    evento.setNombre(resultSet.getString("nombre"));
                    evento.setFecha(String.valueOf(resultSet.getDate("fecha")));
                    eventos.add(evento);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Se ha producido un error al obtener los eventos desde BBDD", e);
        }
        return eventos;
    }

    public static void insertarEvento(EventoModelo eventoModelo) {
        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERTAR_EVENTO_QUERY) ) {

            preparedStatement.setInt(1, eventoModelo.getIdCalendario());
            preparedStatement.setString(2, eventoModelo.getNombre());
            preparedStatement.setString(3, eventoModelo.getFecha());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

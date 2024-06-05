package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.DataItem;
import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseManager {

    private static final String CREAR_PISO_QUERY = "INSERT INTO Piso (id, nombre) " +
            "VALUES (?, ?);";

    private static final String CREAR_CALENDARIO_QUERY = "INSERT INTO calendario (id_piso) VALUES (?)";

    private static final String ACTUALIZAR_USUARIO_QUERY = "UPDATE Usuario " +
            "SET id_piso = ? " +
            "WHERE email = ?";
    private static final String VALIDAR_PISO_QUERY = "SELECT COUNT(*) " +
            "FROM Piso " +
            "WHERE id = ?";

    private static final String OBTENER_ID_QUERY = "SELECT id_piso " +
            "FROM usuario " +
            "WHERE email = ?";

    private final static String TAREAS_QUERY = "SELECT * " +
            "FROM tarea " +
            "WHERE id_piso = ? " +
            "ORDER BY fecha_limite DESC " +
            "LIMIT 3 ";
    private final static String EVENTO_QUERY = "SELECT * " +
            "FROM evento " +
            "WHERE id_calendario IN " +
            "(SELECT id " +
            "FROM Calendario " +
            "WHERE id_piso = ?) " +
            "ORDER BY fecha DESC " +
            "LIMIT 3";


    public boolean insertarPiso(PisoModelo pisoModelo) {

        try (final Connection connection = ConnectionService.getConnection()) {

            try (final PreparedStatement preparedStatement = connection.prepareStatement(CREAR_PISO_QUERY)) {

                preparedStatement.setString(1,pisoModelo.getId());
                preparedStatement.setString(2, pisoModelo.getNombre());
                int filasInsertadas = preparedStatement.executeUpdate();
                return filasInsertadas > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertarCalendario(String pisoId) {
        try (Connection connection = ConnectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREAR_CALENDARIO_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, pisoId);
            int filasInsertadas = preparedStatement.executeUpdate();

            if (filasInsertadas > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al crear el calendario, no se ha obtenido el ID.");
                    }
                }
            } else {
                return -1; // Indica que no se ha insertado ningún registro
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validarPisoEnBD(String pisoId) {

        try (Connection conn = ConnectionService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(VALIDAR_PISO_QUERY)) {
            stmt.setString(1, pisoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void actualizarUsuarioConPisoId(String pisoId, String emailUsuario) {
        try (Connection conn = ConnectionService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR_USUARIO_QUERY)) {
            stmt.setString(1, pisoId);
            stmt.setString(2, emailUsuario);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String obtenerPisoId(String email) {

        try (final Connection connection = ConnectionService.getConnection() ;
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_ID_QUERY)) {

            preparedStatement.setString(1, email);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("id_piso");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Se ha producido un error al obtener el Id del piso desde BBDD", e);
        }
    }


    public static List<DataItem> extractDataHome(String pisoId) {
        final List<DataItem> datosPiso = new ArrayList<>();

        try (final Connection connection = ConnectionService.getConnection()) {
            try (final PreparedStatement preparedStatementTareas = connection.prepareStatement(TAREAS_QUERY);
                 final PreparedStatement preparedStatementEventos = connection.prepareStatement(EVENTO_QUERY)) {

                preparedStatementTareas.setString(1, pisoId);
                preparedStatementEventos.setString(1, pisoId);

                try (final ResultSet resultSet = preparedStatementTareas.executeQuery()) {
                    while (resultSet.next()) {
                        String nombreTarea = resultSet.getString("nombre");
                        Date fechaTarea = resultSet.getDate("fecha_limite");
                        datosPiso.add(new DataItem(nombreTarea, fechaTarea));
                    }
                }
                try (final ResultSet resultSetEventos = preparedStatementEventos.executeQuery()) {
                    while (resultSetEventos.next()) {
                        String nombreEvento = resultSetEventos.getString("nombre");
                        Date fechaEvento = resultSetEventos.getDate("fecha");
                        datosPiso.add(new DataItem(nombreEvento, fechaEvento));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return datosPiso;
    }


}

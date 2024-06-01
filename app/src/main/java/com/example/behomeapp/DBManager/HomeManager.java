package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeManager {

    private static final String CREAR_PISO_QUERY = "INSERT INTO Piso (id, nombre) VALUES (?, ?);";
    private static final String ACTUALIZAR_USUARIO_QUERY = "UPDATE Usuario SET id_piso = ? WHERE email = ?";
    private static final String VALIDAR_PISO_QUERY = "SELECT COUNT(*) FROM Piso WHERE id = ?";
    private final static String QUERY_TAREAS = "SELECT description, date FROM tareas ORDER BY date DESC LIMIT 3";
    private static final String OBTENER_ID_QUERY = "SELECT id_piso " +
            "FROM usuario " +
            "WHERE email = ?";

    private final static String RESUMEN_QUERY = "SELECT * FROM (" +
            " SELECT 'tarea' AS tipo, " +
            "nombre, " +
            "fecha_limite AS fecha " +
            "FROM Tarea " +
            "WHERE id_piso_asignado = ? " +
            "ORDER BY fecha_limite " +
            "DESC LIMIT 3" +
            " UNION" +
            " SELECT 'evento' AS tipo, " +
            "nombre, fecha " +
            "FROM Evento " +
            "INNER JOIN Calendario " +
            "ON Evento.id_calendario = Calendario.id " +
            "WHERE Calendario.id_piso = ? " +
            "ORDER BY fecha " +
            "DESC LIMIT 3" +
            ") AS datos " +
            "ORDER BY fecha DESC LIMIT 3";

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

    public static String obtenerId(String email) {
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
            throw new RuntimeException(e);
        }
    }

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

    public List<String> extractDataHome(String pisoId) {
        List<String> pisoModeloList = new ArrayList<>();

        try (Connection conn = ConnectionService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(RESUMEN_QUERY)) {
            stmt.setString(1, pisoId);
            stmt.setString(2, pisoId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String nombre = rs.getString("nombre");
                pisoModeloList.add(String.valueOf(new PisoModelo(tipo, nombre)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pisoModeloList;
    }
}

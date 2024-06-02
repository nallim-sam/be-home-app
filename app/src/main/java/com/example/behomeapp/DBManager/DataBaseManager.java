package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.model.TareaModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataBaseManager {

    private static final String CREAR_PISO_QUERY = "INSERT INTO Piso (id, nombre) " +
            "VALUES (?, ?);";
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
            "FROM tareas " +
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


    // Variables para la creacion de tareas automaticas
    private static final String INSERTAR_TAREA_QUERY = "INSERT INTO Tarea " + //
            "(nombre, " + //
            "id_usuario_asignado, " +//
            "id_piso_asignado, " +//
            "fecha_limite, " +//
            "frecuencia, " +//
            "completado) " +//
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final int LIMITE_SEMANAS = 52; // Un año

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
            throw new RuntimeException("Se ha producido un error al obtener el Id del piso desde BBDD", e);
        }
    }

    private void extraerEventosResumen(Connection connection, String pisoId) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(EVENTO_QUERY)) {
            preparedStatement.setString(1, pisoId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> extraerTareasResumen(Connection connection, String pisoId) {
        final List<String> tareas = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(TAREAS_QUERY)) {
            preparedStatement.setString(1, pisoId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                   String nombreTareas = resultSet.getString("nombre");
                   tareas.add(nombreTareas);
                }
                return tareas;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> extractDataHome(String pisoId) {
        List<String> datosPiso = new ArrayList<>();


        return datosPiso;
    }

    // Método para crear tareas semanales
    public static void crearTareasSemanales(Date fechaInicio, TareaModelo tareaModelo, Connection connection) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        // Establecer la fecha límite inicial para la tarea base
        Date fechaLimite = tareaModelo.getFechaLimite();

        try {
            // Consulta SQL para insertar una nueva tarea

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(INSERTAR_TAREA_QUERY);

            for (int i = 0; i < LIMITE_SEMANAS; i++) {
                // Avanzar una semana
                calendar.add(Calendar.WEEK_OF_YEAR, 1);

                // Verificar si se ha alcanzado el límite temporal
                if (calendar.get(Calendar.YEAR) > calendar.getActualMaximum(Calendar.YEAR)) {
                    break; // Detener la creación de tareas adicionales
                }

                // Establecer la nueva fecha límite para la tarea
                preparedStatement.setString(1, tareaModelo.getNombre());
                preparedStatement.setInt(2, tareaModelo.getUsuario().getId());
                preparedStatement.setString(3, tareaModelo.getPiso().getId());
                preparedStatement.setDate(4, new java.sql.Date(calendar.getTimeInMillis()));
                preparedStatement.setString(5, tareaModelo.getFrecuencia().toString());
                preparedStatement.setBoolean(6, tareaModelo.isCompletado());

                // Ejecutar la consulta para insertar la nueva tarea
                preparedStatement.executeUpdate();
            }

            // Cerrar el PreparedStatement
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar el error
        }
    }
}

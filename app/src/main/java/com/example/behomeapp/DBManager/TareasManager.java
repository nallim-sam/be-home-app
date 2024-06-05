package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.TareaModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Objects;

/**
 * Clase que gestiona las tareas en la base de datos.
 */
public class TareasManager {

    private static final String TAREAS_DIA_QUERY = "SELECT * " +
            "FROM Tarea " +
            "WHERE fecha_limite = ? " +
            "AND id_piso = ?";
    private static final String TAREAS_SEMANA_QUERY = "SELECT *" +
            " FROM Tarea " +
            "WHERE fecha_limite >= ? " +
            "AND fecha_limite < ?" +
            "AND id_piso = ?";
    private static final String TAREAS_MES_QUERY = "SELECT * " +
            "FROM Tarea " +
            "WHERE fecha_limite >= ? " +
            "AND fecha_limite < ?" +
            "AND id_piso = ?";
    private static final String INSERTAR_TAREA_QUERY = "INSERT INTO Tarea " +
            "(nombre, id_usuario, id_piso, fecha_limite, frecuencia, completado) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final int LIMITE_SEMANAS = 52; // Un año

    public static List<TareaModelo> setupRecyclerViewDia(String pisoId) {
        return getTareas(TAREAS_DIA_QUERY, pisoId, getCurrentDate(), null);
    }

    public static List<TareaModelo> setupRecyclerViewSemana(String pisoId) {
        return getTareas(TAREAS_SEMANA_QUERY, pisoId, getStartDateOfWeek(), getEndDateOfWeek());
    }

    public static List<TareaModelo> setupRecyclerViewMes(String pisoId) {
        return getTareas(TAREAS_MES_QUERY, pisoId, getStartDateOfMonth(), getEndDateOfMonth());
    }

    private static List<TareaModelo> getTareas(String query, String pisoId, Date startDate, Date endDate) {
        List<TareaModelo> tareasList = new ArrayList<>();

        try (Connection connection = ConnectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, startDate);
            if (endDate != null) {
                preparedStatement.setDate(2, endDate);
                preparedStatement.setString(3, pisoId);
            } else {
                preparedStatement.setString(2, pisoId);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    boolean completado = resultSet.getBoolean("completado");
                    tareasList.add(new TareaModelo(nombre, completado));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tareasList;
    }

    private static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private static Date getStartDateOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return new Date(calendar.getTimeInMillis());
    }

    private static Date getEndDateOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }

    private static Date getStartDateOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    private static Date getEndDateOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }


    /**
     * Inserta la tarea en la BBDD
     *
     * @param tarea objeto Tarea obtenido de los datos insertados por el usuario
     */
    public static void insertarTarea(TareaModelo tarea) {

        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERTAR_TAREA_QUERY)) {

            preparedStatement.setString(1, tarea.getNombre());
            preparedStatement.setString(2, tarea.getIdUsuario());
            preparedStatement.setString(3, tarea.getIdPiso());
            preparedStatement.setString(4, tarea.getFechaLimite());
            preparedStatement.setString(5, tarea.getFrecuencia().name().toLowerCase());
            preparedStatement.setBoolean(6, false); // false porque inicialmente la tarea no está completada

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertarTareasConFrecuencia(TareaModelo tarea) {
        Calendar calendar = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(Objects.requireNonNull(sdf.parse(tarea.getFechaLimite())));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < LIMITE_SEMANAS; i++) {
            switch (tarea.getFrecuencia()) {
                case DIARIO:
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                case SEMANAL:
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case MENSUAL:
                    calendar.add(Calendar.MONTH, 1);
                    break;
                default:
                    break;
            }

            if (calendar.get(Calendar.YEAR) > Calendar.getInstance().getActualMaximum(Calendar.YEAR)) {
                break;
            }

            TareaModelo nuevaTarea = new TareaModelo();
            nuevaTarea.setNombre(tarea.getNombre());
            nuevaTarea.setIdUsuario(tarea.getIdUsuario());
            nuevaTarea.setIdPiso(tarea.getIdPiso());
            nuevaTarea.setFechaLimite(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
            nuevaTarea.setFrecuencia(tarea.getFrecuencia());
            nuevaTarea.setCompletado(tarea.isCompletado());

            insertarTarea(nuevaTarea);
        }

    }

}

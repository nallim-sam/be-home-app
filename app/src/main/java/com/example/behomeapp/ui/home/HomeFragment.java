package com.example.behomeapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.behomeapp.R;
import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.service.ConnectionService;
import com.example.behomeapp.service.TareasActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private final static String QUERY = "SELECT * FROM (" +
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
    private TextView idTextView;
    private Button tareasButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        idTextView = view.findViewById(R.id.idFromDB);
        tareasButton = view.findViewById(R.id.buttonCrearTarea);

        // Obtener idPiso de SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String pisoId = prefs.getString("id_piso", null);

        // Mostrar el id_piso en el TextView
        if (pisoId != null) {
            idTextView.setText("Piso ID: " + pisoId);
        }

        tareasButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TareasActivity.class);
            startActivity(intent);
        });

        return view;
    }

    protected List<PisoModelo> extractDataHome(String pisoId) {
        List<PisoModelo> pisoModeloList = new ArrayList<>();

        try (Connection conn = ConnectionService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            stmt.setString(1, pisoId);
            stmt.setString(2, pisoId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha");
                pisoModeloList.add(new PisoModelo(tipo, nombre));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return pisoModeloList;
    }
}
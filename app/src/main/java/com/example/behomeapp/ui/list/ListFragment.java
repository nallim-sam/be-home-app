package com.example.behomeapp.ui.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behomeapp.R;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ListFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<String> listNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener el id_piso guardado en SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String idPiso = sharedPreferences.getString("id_piso", "");

        // Realizar la conexión a la base de datos
        try (Connection connection = ConnectionService.getConnection()) {
            // Método para obtener la conexión a la base de datos

            // Consultar las listas asociadas al id_piso en la base de datos
            if (connection != null) {
                try {
                    String query = "SELECT nombre FROM lista WHERE id_piso = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, idPiso);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        String nombreLista = resultSet.getString("nombre");
                        listNames.add(nombreLista);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("",e);
        }


    }

}

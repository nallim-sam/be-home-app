package com.example.behomeapp.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behomeapp.DBManager.HomeManager;
import com.example.behomeapp.R;

import java.util.List;


public class ResumenFragment extends Fragment {

    private RecyclerViewAdapter adapter;
    private List<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        final HomeManager homeManager = new HomeManager();
        final TextView idTextView = view.findViewById(R.id.idFromDB);
        final RecyclerView recyclerView = view.findViewById(R.id.resumen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener idPiso de SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String email = prefs.getString("email", null);


        new Thread(() -> {
            try {
                final String pisoId = HomeManager.obtenerId(email);
                if (pisoId.isEmpty()) {
                    getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "No se ha encontrado el id del piso", Toast.LENGTH_LONG).show());
                    return;
                }

                // Actualizar la UI en el hilo principal
                getActivity().runOnUiThread(() -> idTextView.setText(pisoId));

                data = homeManager.extractDataHome(pisoId);

                // Actualizar la UI en el hilo principal
                getActivity().runOnUiThread(() -> {
                    adapter = new RecyclerViewAdapter(getContext(), data);
                    recyclerView.setAdapter(adapter);
                });

            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Error al obtener los datos", Toast.LENGTH_LONG).show());
            }
        }).start();

        return view;
    }
}
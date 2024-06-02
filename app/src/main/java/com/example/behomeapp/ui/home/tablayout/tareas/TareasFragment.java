package com.example.behomeapp.ui.home.tablayout.tareas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.behomeapp.CrearTareaFragment;
import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.DBManager.TareaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.TareaModelo;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.util.List;
import java.util.logging.Logger;


public class TareasFragment extends Fragment {

    private static final Logger log = Logger.getLogger(TareasFragment.class.getName());

    private TareasAdapter adapterDia;
    private TareasAdapter adapterSemana;
    private TareasAdapter adapterMes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        final RecyclerView recyclerViewDia = view.findViewById(R.id.recyclerViewDia);
        final RecyclerView recyclerViewSemana = view.findViewById(R.id.recyclerViewSemana);
        final RecyclerView recyclerViewMes = view.findViewById(R.id.recyclerViewMes);
        final Button buttonCrearTareas = view.findViewById(R.id.buttonCrearTareas);

        // Asignar LayoutManager a los RecyclerView
        recyclerViewDia.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSemana.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener idPiso de SharedPreferences
        final String email = SharedPreferencesUtils.getEmail( requireContext());
        if (email.isEmpty()) {
            log.info("El email NO se ha obtenido correctamente de sharedPreferences");
        }

        new Thread(() -> {

            final String pisoId = DataBaseManager.obtenerId(email);

            if (pisoId == null || pisoId.isEmpty()) {
                log.info("El ID del piso está vacío.");
            }

            final List<TareaModelo> tareasDiaList = TareaManager.setupRecyclerViewDia(pisoId);
            final List<TareaModelo> tareasSemanaList = TareaManager.setupRecyclerViewSemana(pisoId);
            final List<TareaModelo> tareasMesList = TareaManager.setupRecyclerViewMes(pisoId);

            // Actualizar la UI en el hilo principal
            runOnUiThreadSafe(() -> {

                adapterDia = new TareasAdapter(getContext(), tareasDiaList);
                recyclerViewDia.setAdapter(adapterDia);

                adapterSemana = new TareasAdapter(getContext(), tareasSemanaList);
                recyclerViewSemana.setAdapter(adapterSemana);

                adapterMes = new TareasAdapter(getContext(), tareasMesList);
                recyclerViewMes.setAdapter(adapterMes);



            });

        }).start();

        buttonCrearTareas.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CrearTareaFragment.class);
            startActivity(intent);
        });

        return view;
    }

    /**
     * Método auxiliar para ejecutar en el hilo principal de forma segura
     *
     * @param action objeto de la interfaz funcional Runnable
     */
    private void runOnUiThreadSafe(Runnable action) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(action);
        }
    }


}
package com.example.behomeapp.ui.home.tablayout.tareas;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.DBManager.TareasManager;
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

        final TextView textViewNoTareasDia = view.findViewById(R.id.textViewNoTareasDia);
        final TextView textViewNoTareasSemana = view.findViewById(R.id.textViewNoTareasSemana);
        final TextView textViewNoTareasMes = view.findViewById(R.id.textViewNoTareasMes);

        // Asignar LayoutManager a los RecyclerView
        recyclerViewDia.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSemana.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener idPiso de SharedPreferences
        final String email = SharedPreferencesUtils.getUserEmail( requireContext());
        if (email.isEmpty()) {
            log.info("El email NO se ha obtenido correctamente de sharedPreferences");
        }

        new Thread(() -> {

            final String pisoId = DataBaseManager.obtenerPisoId(email);

            if (pisoId == null || pisoId.isEmpty()) {
                log.info("El ID del piso está vacío.");
            }

            final List<TareaModelo> tareasDiaList = TareasManager.setupRecyclerViewDia(pisoId);
            final List<TareaModelo> tareasSemanaList = TareasManager.setupRecyclerViewSemana(pisoId);
            final List<TareaModelo> tareasMesList = TareasManager.setupRecyclerViewMes(pisoId);

            runOnUiThreadSafe(() -> {
                if (tareasDiaList.isEmpty()) {
                    textViewNoTareasDia.setVisibility(View.VISIBLE);
                    recyclerViewDia.setVisibility(View.GONE);
                } else {
                    textViewNoTareasDia.setVisibility(View.GONE);
                    recyclerViewDia.setVisibility(View.VISIBLE);
                    adapterDia = new TareasAdapter(getContext(), tareasDiaList);
                    recyclerViewDia.setAdapter(adapterDia);
                }
                if (tareasSemanaList.isEmpty()) {
                    textViewNoTareasSemana.setVisibility(View.VISIBLE);
                    recyclerViewSemana.setVisibility(View.GONE);
                } else {
                    textViewNoTareasSemana.setVisibility(View.GONE);
                    recyclerViewSemana.setVisibility(View.VISIBLE);
                    adapterSemana = new TareasAdapter(getContext(), tareasSemanaList);
                    recyclerViewSemana.setAdapter(adapterSemana);
                }
                if (tareasMesList.isEmpty()) {
                    textViewNoTareasMes.setVisibility(View.VISIBLE);
                    recyclerViewMes.setVisibility(View.GONE);
                } else {
                    textViewNoTareasMes.setVisibility(View.GONE);
                    recyclerViewMes.setVisibility(View.VISIBLE);
                    adapterMes = new TareasAdapter(getContext(), tareasMesList);
                    recyclerViewMes.setAdapter(adapterMes);
                }
            });


        }).start();

        buttonCrearTareas.setOnClickListener(v -> {
            CrearTareaFragment crearTareaFragment = new CrearTareaFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, crearTareaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
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
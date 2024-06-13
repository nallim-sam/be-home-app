package com.example.behomeapp.ui.home.tablayout;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.DataItem;
import com.example.behomeapp.ui.home.ResumenAdapter;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.util.logging.Logger;

import java.util.List;


public class ResumenFragment extends Fragment {

    private static final Logger log = Logger.getLogger(ResumenFragment.class.getName());
    private static final String ERROR_TEXT = "ID del piso no disponible";

    private ResumenAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        final TextView idTextView = view.findViewById(R.id.idFromDB);
        final RecyclerView recyclerView = view.findViewById(R.id.resumen);

        final TextView textViewNoResumen = view.findViewById(R.id.textViewNoResumen);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener idPiso de SharedPreferences
        final String email = SharedPreferencesUtils.getUserEmail(requireContext());
        if (email == null || email.isEmpty()) {
            log.info("El email NO se ha obtenido correctamente de sharedPreferences");
            return view;
        }

        new Thread(() -> {

            final String pisoId = DataBaseManager.obtenerPisoId(email);
            if (pisoId == null || pisoId.isEmpty()) {
                log.info("El ID del piso está vacío.");
                runOnUiThreadSafe(() -> idTextView.setText(ERROR_TEXT));
                return;
            }

            // Actualizar la UI en el hilo principal
            runOnUiThreadSafe(() -> idTextView.setText(pisoId));

            final List<DataItem> data = DataBaseManager.extractDataHome(pisoId);


            requireActivity().runOnUiThread(() -> {
                if (data.isEmpty()) {
                    textViewNoResumen.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    adapter = new ResumenAdapter(getContext(), data);
                    recyclerView.setAdapter(adapter);
                    textViewNoResumen.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            });
        }).start();

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
package com.example.behomeapp.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ListaCompraModelo;

import com.example.behomeapp.util.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.logging.Logger;

public class ListFragment extends Fragment {


    private static final Logger log = Logger.getLogger(ListFragment.class.getName());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        final ListView listViewCompras = view.findViewById(R.id.listViewListas);
        final FloatingActionButton fabAddList = view.findViewById(R.id.button_add_list);

        // Obtener idPiso de SharedPreferences
        final String email = SharedPreferencesUtils.getEmail(requireContext());
        if (email.isEmpty()) {
            log.info("El email NO se ha obtenido correctamente de sharedPreferences");
        }

        new Thread(() -> {

            final String pisoId = DataBaseManager.obtenerPisoId(email);
            if (pisoId == null || pisoId.isEmpty()) {
                log.info("El ID del piso está vacío.");
                return;
            }
            final List<ListaCompraModelo> listaComprasList = ListaManager.obtenerListasCompras(pisoId);

            runOnUiThreadSafe(() -> {
                ListaComprasAdapter adapter = new ListaComprasAdapter(getContext(), listaComprasList);
                listViewCompras.setAdapter(adapter);

                listViewCompras.setOnItemClickListener((parent, view1, position, id) -> {

                    ListaCompraModelo selectedLista = listaComprasList.get(position);

                    new Thread(() -> {
                        // Aquí agregamos la lógica para obtener el ID de la lista
                        int idLista = ListaManager.obtenerIdLista(pisoId, selectedLista.getNombre());
                        selectedLista.setId(idLista);

                        runOnUiThreadSafe( () -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id_lista", idLista);
                            bundle.putString("nombre_lista", selectedLista.getNombre());

                            NavController navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_navigation_list_to_lista_productos, bundle);
                        });

                    }).start();

                });

            });

        }).start();

        fabAddList.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_navigation_list_to_crearListaFragment);
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

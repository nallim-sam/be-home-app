package com.example.behomeapp.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ListaCompraModelo;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.util.logging.Logger;

public class CrearListaFragment extends Fragment {

    private static final Logger log = Logger.getLogger(CrearListaFragment.class.getName());
    private EditText editTextNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_lista, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombreLista);

        final Button buttonCrearLista = view.findViewById(R.id.buttonCrearLista);
        final Button buttonVolver = view.findViewById(R.id.buttonVolver);

        buttonCrearLista.setOnClickListener(v -> new Thread(this::crearLista).start());

        buttonVolver.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    private void crearLista() {

        String nombreLista = editTextNombre.getText().toString().trim();

        if (nombreLista.isEmpty()) {
            log.info("No se ha podido crear la lista porque el nombre está vacío.");
            return;
        }

        ListaCompraModelo listaCompraModelo = new ListaCompraModelo();
        listaCompraModelo.setNombre(nombreLista);

        final String email = SharedPreferencesUtils.getUserEmail(requireContext());
        final String pisoId = DataBaseManager.obtenerPisoId(email);

        ListaManager.insertarLista(nombreLista, pisoId);

        // Regresar al fragmento anterior o cerrar el fragmento actual
        getActivity().getSupportFragmentManager().popBackStack();

    }
}
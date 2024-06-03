package com.example.behomeapp.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;

import java.util.logging.Logger;


public class CrearProductoFragment extends Fragment {

    private static final Logger log = Logger.getLogger(CrearProductoFragment.class.getName());
    private EditText editTextNombreProducto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_producto, container, false);

        editTextNombreProducto = view.findViewById(R.id.editTextNombreProducto);

        final Button buttonAddProducto = view.findViewById(R.id.buttonAddProducto);
        final Button buttonVolver = view.findViewById(R.id.buttonVolver);

        buttonAddProducto.setOnClickListener(v -> new Thread(this::crearProducto).start());
        buttonVolver.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    private void crearProducto() {

        String nombreProducto = editTextNombreProducto.getText().toString().trim();

        if (nombreProducto.isEmpty()) {
            log.info("No se ha podido crear la lista porque el nombre está vacío.");
            return;
        }

        Bundle args = getArguments();
        if (args != null) {
            int idLista = args.getInt("id_lista");
            ListaManager.insertarProducto(nombreProducto,idLista);
        }
        log.info("No se ha podido insertar un producto en la lista porque no se ha encontrado el identificador.");

    }
}
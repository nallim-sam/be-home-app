package com.example.behomeapp.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ProductoModelo;

import java.util.List;
import java.util.logging.Logger;

public class CrearProductoFragment extends Fragment {

    private static final Logger log = Logger.getLogger(CrearProductoFragment.class.getName());
    private EditText editTextNombreProducto;
    private List<ProductoModelo> listaProductos;


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

    /**
     * Crea un producto y lo añade a una lista
     */
    private void crearProducto() {
        String nombreProducto = editTextNombreProducto.getText().toString().trim();
        if (nombreProducto.isEmpty()) {
            log.info("No se ha podido crear la lista porque el nombre está vacío.");
            return;
        }
        Bundle args = getArguments();
        if (args != null) {
            int idLista = args.getInt("id_lista");
            ProductoModelo nuevoProducto = ListaManager.insertarProducto(nombreProducto, idLista);

            if (nuevoProducto != null) {
                // Añadir el nuevo producto a la lista local de productos
                if (listaProductos != null) {
                    listaProductos.add(nuevoProducto);
                    Toast.makeText(getContext(), "Producto añadido", Toast.LENGTH_LONG).show();

                    // Regresar al fragmento anterior o cerrar el fragmento actual
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    log.warning("La lista de productos no está inicializada.");
                }
            }
        } else {
            log.info("No se ha podido insertar un producto en la lista porque no se ha encontrado el identificador.");
        }
    }


    /**
     * Inicializa la lista de productos
     *
     * @param listaProductos lista del modelo Producto
     */
    public void setListaProductos(List<ProductoModelo> listaProductos) {
        this.listaProductos = listaProductos;
    }


}
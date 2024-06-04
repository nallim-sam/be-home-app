package com.example.behomeapp.ui.list;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ProductoModelo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaProductosFragment extends Fragment {

    private String nombreLista;
    private int listaId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_productos, container, false);

        TextView textViewNombreLista = view.findViewById(R.id.text_lista_nombre);
        ListView listViewProductos = view.findViewById(R.id.listViewProductos);
        FloatingActionButton fabAddProduct = view.findViewById(R.id.fabAddProduct);

        new Thread(() -> {

            // Obtener el id de la lista de la compra seleccionada
            Bundle args = getArguments();
            if (args != null) {
                nombreLista = args.getString("nombre_lista");
                listaId =  args.getInt("id_lista");
            }
            // Cargar los productos de la lista desde la base de datos
            final List<ProductoModelo> listaProductos = ListaManager.obtenerProductosLista(listaId);

            runOnUiThreadSafe(() -> {
                textViewNombreLista.setText(nombreLista);
                ProductosAdapter productosAdapter = new ProductosAdapter(getContext(), listaProductos);
                listViewProductos.setAdapter(productosAdapter);
            });

        }).start();

        fabAddProduct.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putInt("id_lista", listaId);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_lista_productos_to_crearProductoFragment, bundle);
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
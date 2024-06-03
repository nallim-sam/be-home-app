package com.example.behomeapp.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.behomeapp.DBManager.ListaManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ProductoModelo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaProductosFragment extends Fragment {

    private int idLista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_productos, container, false);

        ListView listViewProductos = view.findViewById(R.id.listViewProductos);
        FloatingActionButton fabAddProduct = view.findViewById(R.id.fabAddProduct);


        // Obtener el id de la lista de la compra seleccionada
        Bundle args = getArguments();
        if (args != null) {
            idLista = args.getInt("id_lista");
        }

        // Configurar el adapter
        List<ProductoModelo> productos = new ArrayList<>();
        ProductosAdapter adapter = new ProductosAdapter(getActivity(), productos);
        listViewProductos.setAdapter(adapter);

        // Cargar los productos de la lista desde la base de datos
        ListaManager.obtenerProductosLista(idLista);

        // Configurar el click listener para el FAB
        fabAddProduct.setOnClickListener(v ->  {

            CrearProductoFragment crearProductoFragment = new CrearProductoFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

            transaction.replace(R.id.lista_productos, crearProductoFragment);
            transaction.addToBackStack(null);
            transaction.commit();


        });

        return view;
    }
}
package com.example.behomeapp.ui.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.behomeapp.DBManager.ListaCompraManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.ListaCompraModelo;

import java.util.List;


public class ListaComprasAdapter extends ArrayAdapter<ListaCompraModelo> {

    public ListaComprasAdapter(Context context, List<ListaCompraModelo> listasCompras) {
        super(context, 0, listasCompras);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaCompraModelo listaCompra = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView nombreTextView = convertView.findViewById(R.id.tvListName);
        ImageView deleteImageView = convertView.findViewById(R.id.imageViewEliminarLista);

        nombreTextView.setText(listaCompra.getNombre());
        // Configuración del clic del botón para eliminar la lista
        deleteImageView.setOnClickListener(v -> new Thread(() -> {

            ListaCompraManager.eliminarLista(listaCompra);

            ((Activity) getContext()).runOnUiThread(() -> {
                remove(listaCompra);
                notifyDataSetChanged();
            });

        }).start());


        return convertView;
    }
}
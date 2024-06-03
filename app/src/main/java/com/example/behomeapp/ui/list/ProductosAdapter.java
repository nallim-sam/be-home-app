package com.example.behomeapp.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.behomeapp.R;
import com.example.behomeapp.model.ProductoModelo;

import java.util.List;

public class ProductosAdapter extends ArrayAdapter<ProductoModelo> {
    public ProductosAdapter(Context context, List<ProductoModelo> productos) {
        super(context, 0, productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductoModelo producto = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView tvNombre = convertView.findViewById(R.id.tvItemName);
        tvNombre.setText(producto.getNombre());
        return convertView;
    }
}

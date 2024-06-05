package com.example.behomeapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behomeapp.R;
import com.example.behomeapp.model.DataItem;

import java.util.List;

/**
 * Adaptador para proporcionar datos a un RecyclerView
 */
public class ResumenAdapter extends RecyclerView.Adapter<ResumenAdapter.ViewHolder>{

    private final List<DataItem> data;
    private final LayoutInflater inflater;

    public ResumenAdapter(Context context, List<DataItem> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_resumen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumenAdapter.ViewHolder holder, int position) {
        DataItem currentItem = data.get(position);
        holder.textView.setText(currentItem.getNombre());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewResumen);
        }
    }
}

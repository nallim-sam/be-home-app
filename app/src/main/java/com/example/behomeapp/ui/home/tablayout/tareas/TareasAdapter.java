package com.example.behomeapp.ui.home.tablayout.tareas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behomeapp.R;
import com.example.behomeapp.model.TareaModelo;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {
private final List<TareaModelo> tareasList;
private final Context context;

public TareasAdapter(Context context, List<TareaModelo> tareasList) {
        this.context = context;
        this.tareasList = tareasList;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TareaModelo tarea = tareasList.get(position);
        holder.nombreTarea.setText(tarea.getNombre());
        holder.checkBox.setChecked(tarea.isCompletado());
        }

@Override
public int getItemCount() {
        return tareasList.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView nombreTarea;
    CheckBox checkBox;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreTarea = itemView.findViewById(R.id.nombreTarea);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}
}
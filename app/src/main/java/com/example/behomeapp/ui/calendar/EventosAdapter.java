package com.example.behomeapp.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behomeapp.R;
import com.example.behomeapp.model.EventoModelo;

import java.util.List;

public class EventosAdapter  extends RecyclerView.Adapter<EventosAdapter.EventoViewHolder> {


    private final List<EventoModelo> eventosList;
    private final Context context;

    public EventosAdapter(Context context, List<EventoModelo> eventosList) {
        this.context = context;
        this.eventosList = eventosList;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        EventoModelo evento = eventosList.get(position);
        holder.textViewNombre.setText(evento.getNombre());
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
        }
    }

}

package com.example.behomeapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.behomeapp.DBManager.HomeManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.service.ConnectionService;
import com.example.behomeapp.service.TareasActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ResumenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        TextView idTextView = view.findViewById(R.id.idFromDB);
        RecyclerView recyclerView = view.findViewById(R.id.resumen);

        // Obtener idPiso de SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String email = prefs.getString("email", null);
        final String pisoId = HomeManager.obtenerId(email);
        idTextView.setText(pisoId);

        //cargarDatos(pisoId, recyclerView);

        return view;
    }
}
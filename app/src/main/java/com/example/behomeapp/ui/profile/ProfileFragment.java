package com.example.behomeapp.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.behomeapp.DBManager.UserManager;
import com.example.behomeapp.R;
import com.example.behomeapp.login.LoginActivity;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView textViewUsername = view.findViewById(R.id.tvUsername);
        final TextView textViewEmail = view.findViewById(R.id.tvEmail);

        final Button btnLogout = view.findViewById(R.id.buttonLogout);

        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String email = prefs.getString("email", null);
        new Thread(() -> {
            String username = UserManager.obtenerNombre(email);

            // Actualizar la interfaz de usuario en el hilo principal
            requireActivity().runOnUiThread(() -> {
                textViewEmail.setText(email);
                textViewUsername.setText(username);
            });
        }).start();

        btnLogout.setOnClickListener( v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        return view;
    }


}
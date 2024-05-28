package com.example.behomeapp.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behomeapp.R;
import com.example.behomeapp.inserter.PisoInserter;
import com.example.behomeapp.model.PisoModelo;
import com.example.behomeapp.ui.home.HomeFragment;

import java.security.SecureRandom;

public class CrearPisoActivity extends AppCompatActivity {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private EditText etName;
    private EditText etId;
    private final PisoInserter pisoInserter = new PisoInserter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_piso);

        etName = findViewById(R.id.editTextName);
        etId = findViewById(R.id.editTextId);

        final Button buttonCreateHome = findViewById(R.id.buttonCreateHome);
        final Button buttonJoinHome = findViewById(R.id.buttonJoinHome);


        buttonCreateHome.setOnClickListener(v -> crearPiso());

        buttonJoinHome.setOnClickListener(v -> unirsePiso());
    }

    private void crearPiso() {

        final String name = etName.getText().toString().trim();
        final String id = crearIdPiso();

        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor, introduce el nombre del piso", Toast.LENGTH_SHORT).show();
            return;
        }

        final PisoModelo pisoModelo = new PisoModelo();
        pisoModelo.setId(id);
        pisoModelo.setNombre(name);

        //final PisoInserter pisoInserter = new PisoInserter();

        final boolean pisoInsertado = pisoInserter.insertarPiso(pisoModelo);

        if (pisoInsertado) {
            // Guardar id del piso en SharedPreferences
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id_piso", id);
            editor.apply();

            // Navegar al HomeFragment
            Intent intent = new Intent(CrearPisoActivity.this, HomeFragment.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al crear el piso", Toast.LENGTH_SHORT).show();
        }
    }

    private String crearIdPiso() {
        StringBuilder codigo = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            codigo.append(CHARACTERS.charAt(index));
        }
        return codigo.toString();
    }

    private void unirsePiso() {

        String pisoId = etId.getText().toString().trim();

        if (pisoId.isEmpty()) {
            Toast.makeText(CrearPisoActivity.this, "Por favor, introduce el ID del piso", Toast.LENGTH_SHORT).show();
        }

        if (pisoInserter.validarPisoEnBD(pisoId)) {
            // Guardar id del piso en SharedPreferences
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id_piso", etId.getText().toString().trim());
            editor.apply();

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String emailUsuario = sharedPreferences.getString("email", "");

            // Actualizar el ID del piso para el usuario en la BBDD
            pisoInserter.actualizarUsuarioConPisoId(etId.getText().toString().trim(), emailUsuario);

            // Navegar al HomeFragment
            Intent intent = new Intent(CrearPisoActivity.this, HomeFragment.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(CrearPisoActivity.this, "ID de piso no válido", Toast.LENGTH_SHORT).show();
        }
    }

}

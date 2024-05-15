package com.example.behomeapp.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behome.R;

import java.util.ArrayList;
import java.util.List;

public class CrearPisoActivity extends AppCompatActivity {

    private EditText etName, etUsers;
    private NumberPicker numberPickerComedor, numberPickerSalon, numberPickerCocina,
            numberPickerBano, numberPickerPasillo, numberPickerBalcon;
    private Button buttonCreateHome;
    private ImageButton addImageButton;
    private List<String> usuariosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_piso);

        etName = findViewById(R.id.editTextName);
        etUsers = findViewById(R.id.editTextUsers);

        numberPickerBano = findViewById(R.id.numberPickerBano);
        numberPickerPasillo = findViewById(R.id.numberPickerPasillo);
        numberPickerComedor = findViewById(R.id.numberPickerComedor);
        numberPickerSalon = findViewById(R.id.numberPickerSalon);
        numberPickerCocina = findViewById(R.id.numberPickerCocina);
        numberPickerBalcon = findViewById(R.id.numberPickerBalcon);

        buttonCreateHome = findViewById(R.id.buttonCreateHome);
        addImageButton = findViewById(R.id.addImageButton);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });

        buttonCreateHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });
    }


}

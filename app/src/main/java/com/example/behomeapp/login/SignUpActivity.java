package com.example.behomeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behomeapp.R;
import com.example.behomeapp.service.CrearPisoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private String txtName, txtEmail, txtPassword, txtConfirmPassword;
    private Button buttonRegister, buttonVolver;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        buttonRegister = findViewById(R.id.buttonSignUp);
        buttonVolver = findViewById(R.id.buttonVolver);


        buttonRegister.setOnClickListener(v -> {

            txtName = etName.getText().toString().trim();
            txtEmail = etEmail.getText().toString().trim();
            txtPassword = etPassword.getText().toString().trim();
            txtConfirmPassword = etConfirmPassword.getText().toString().trim();


            if (txtName.isEmpty()) {
                etName.setError("El nombre es un campo obligatorio.");
                return;
            }
            if (txtEmail.isEmpty()) {
                etEmail.setError("Email es un campo obligatorio.");
                return;
            }
            if (!txtEmail.matches(emailPattern)) {
                etEmail.setError("Introduce un email válido.");
                return;
            }
            if (txtPassword.isEmpty()) {
                etPassword.setError("La contraseña es un campo obligatorio.");
                return;
            }
            if (txtConfirmPassword.isEmpty()) {
                etConfirmPassword.setError("La confirmación de contraseña es un campo obligatorio.");
                return;
            }
            if (!txtPassword.equals(txtConfirmPassword)) {
                etConfirmPassword.setError("Las contraseñas deben coincidir.");
            }
            SignUpUser(txtName, txtEmail,txtPassword);
        });

        // Volver a la pagina de Login
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void SignUpUser(String txtName, String txtEmail, String txtPassword) {
        buttonRegister.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(authResult -> {
            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

            // Crear un HashMap para representar los datos del usuario
            Map<String, Object> nuevoUsuario = new HashMap<>();
            nuevoUsuario.put("userId", userId);
            nuevoUsuario.put("nombre", txtName);
            nuevoUsuario.put("email", txtEmail);
            nuevoUsuario.put("contrasenya", txtPassword);

            // Guarda el nuevo usuario en Cloud Firestore
            db.collection("usuario").document(userId).set(nuevoUsuario);

            Toast.makeText(SignUpActivity.this, "Registro Completado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, CrearPisoActivity.class);
            startActivity(intent);

        }).addOnFailureListener(e -> {
            Toast.makeText(SignUpActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            buttonRegister.setVisibility(View.VISIBLE);
        });
    }
}

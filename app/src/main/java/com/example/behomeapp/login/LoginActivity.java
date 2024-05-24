package com.example.behomeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behomeapp.NavActivity;
import com.example.behomeapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin, buttonSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.etSignInEmail);
        editTextPassword = findViewById(R.id.etSignInPassword);

        buttonSignUp = findViewById(R.id.buttonLoginSignUp);
        buttonLogin = findViewById(R.id.buttonLogin);

        // inicalizamos Firebase
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> loginUser());

        // Cambio de pantalla de login a SignIn para poder registrarte
        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loginUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || email.matches(emailPattern)) {
            editTextEmail.setError("El campo email no es correcto.");
        }
        if (password.isEmpty()) {
            editTextPassword.setError("La contraseña no puede estar vacia");
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            Toast.makeText(LoginActivity.this, "Iniciando Sesion", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, NavActivity.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            buttonLogin.setVisibility(View.VISIBLE);
        });
    }
}

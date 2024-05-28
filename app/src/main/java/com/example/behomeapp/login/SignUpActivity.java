package com.example.behomeapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behomeapp.R;
import com.example.behomeapp.service.ConnectionService;
import com.example.behomeapp.service.CrearPisoActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    private static final String SIGNUP_QUERY = "INSERT INTO USUARIO (nombre, email, contrasena) VALUES (?, ?, ?)";
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private String txtName, txtEmail, txtPassword, txtConfirmPassword;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonSignUp);
        Button buttonVolver = findViewById(R.id.buttonVolver);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

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
                return;
            }

            executorService.execute(() -> registerUser(txtName, txtEmail, txtPassword));
        });

        // Volver a la pagina de Login
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String txtName, String txtEmail, String txtPassword) {
        try (final Connection connection = ConnectionService.getConnection()) {

            try (final PreparedStatement preparedStatement = connection.prepareStatement(SIGNUP_QUERY)) {

                preparedStatement.setString(1, txtName);
                preparedStatement.setString(2, txtEmail);
                preparedStatement.setString(3, txtPassword);

                int filasInsertadas = preparedStatement.executeUpdate();

                mainHandler.post(() -> {
                    if (filasInsertadas > 0) {

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", txtEmail);
                        editor.apply();

                        Toast.makeText(SignUpActivity.this, "Registrándose", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, CrearPisoActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (SQLException e) {
            mainHandler.post(() -> Toast.makeText(SignUpActivity.this, "Se ha producido un error al registrar el nuevo usuario.", Toast.LENGTH_SHORT).show());
            throw new RuntimeException("ERROR: Se ha producido un error al acceder a la BBDD", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}

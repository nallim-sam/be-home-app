package com.example.behomeapp.login;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {

    private final static String SIGNUP_QUERY = "INSERT INTO " +
            "USUARIO " +
            "(username, nombre, apellidos, email, contrasena) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]=\\.+[a-z]";

    private EditText etUsername, etName, etLastName, etEmail, etPassword, etConfirmPassword;
    private String txtUsername, txtName, txtLastName, txtEmail, txtPassword, txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonSignUp);
        Button buttonVolver = findViewById(R.id.buttonVolver);

        buttonRegister.setOnClickListener(v -> {

            txtUsername = etUsername.getText().toString().trim();
            txtName = etName.getText().toString().trim();
            txtLastName = etLastName.getText().toString().trim();
            txtEmail = etEmail.getText().toString().trim();
            txtPassword = etPassword.getText().toString().trim();
            txtConfirmPassword = etConfirmPassword.getText().toString().trim();

            if (txtUsername.isEmpty()) {
                etName.setError("El nombre de usuario es un campo obligatorio.");
            }
            if (txtName.isEmpty()) {
                etName.setError("El nombre es un campo obligatorio.");
            }
            if (txtLastName.isEmpty()) {
                etName.setError("El apellido es un campo obligatorio.");
            }
            if (txtEmail.isEmpty()) {
                etEmail.setError("Email es un campo obligatorio.");
            }
            if (txtEmail.matches(emailPattern)) {
                etEmail.setError("Introduce un email válido.");
            }
            if (txtPassword.isEmpty()) {
                etPassword.setError("La contraseña es un campo obligatorio.");
            }
            if (txtConfirmPassword.isEmpty()) {
                etConfirmPassword.setError("La confirmación de contraseña es un campo obligatorio.");
            }
            if (!txtPassword.equals(txtConfirmPassword)) {
                etConfirmPassword.setError("Las contraseñas deben coincidir.");
            }

            registerUser(txtUsername, txtName, txtLastName, txtEmail, txtPassword);
        });

        // Volver a la pagina de Login
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void registerUser(final String txtUsername, final String txtName, final String txtLastName,
                              final String txtEmail, final String txtPassword) {

        try (final Connection connection = ConnectionService.getConnection()) {

            try (final PreparedStatement preparedStatement = connection.prepareStatement(SIGNUP_QUERY)) {

                preparedStatement.setString(1, txtUsername);
                preparedStatement.setString(2, txtName);
                preparedStatement.setString(3, txtLastName);
                preparedStatement.setString(4, txtEmail);
                preparedStatement.setString(5, txtPassword);

                int filasInsertadas = preparedStatement.executeUpdate();

                if (filasInsertadas > 0) {
                    Toast.makeText(SignUpActivity.this, "Registrándose", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad de registro después de un registro exitoso
                }
            }
        } catch (SQLException e) {
            Toast.makeText(SignUpActivity.this, "Se ha producido un error al registrar el nuevo usuario.", Toast.LENGTH_SHORT).show();
            throw new RuntimeException("ERROR: Se ha producido un error al acceder a la BBDD", e);
        }

        Toast.makeText(SignUpActivity.this, "Registro Completado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, CrearPisoActivity.class);
        startActivity(intent);
    }

}

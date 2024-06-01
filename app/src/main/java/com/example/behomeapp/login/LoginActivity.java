package com.example.behomeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.behomeapp.NavActivity;
import com.example.behomeapp.R;
import com.example.behomeapp.service.ConnectionService;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private final static String LOGIN_QUERY = "SELECT * " +
            "FROM usuario " +
            "WHERE email = ? " +
            "AND contrasena = ?";
    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.etSignInEmail);
        editTextPassword = findViewById(R.id.etSignInPassword);

        final Button buttonSignUp = findViewById(R.id.buttonLoginSignUp);
        final Button buttonLogin = findViewById(R.id.buttonLogin);

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

        if (!validarCampos(email, password)) {
            return;
        }

        new Thread(() -> {
            try {
                Connection conn = ConnectionService.getConnection();

                PreparedStatement preparedStatement = conn.prepareStatement(LOGIN_QUERY);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    runOnUiThread(() -> {

                        try {
                            final String name = resultSet.getString("nombre");

                            SharedPreferencesUtils.saveUserData(LoginActivity.this, email, name);

                        } catch (SQLException e) {
                            throw new RuntimeException("Error al obtener el nombre del usuario al iniciar sesión.", e);
                        }


                        Toast.makeText(LoginActivity.this, "Iniciando sesión", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show());
                }

                resultSet.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_LONG).show());
                throw new RuntimeException("ERROR: Se ha producido un error al acceder a la BBDD", e);
            }

        }).start();
    }

    private boolean validarCampos(String email, String password) {
        if (email.isEmpty() || !email.matches(emailPattern)) {
            editTextEmail.setError("El campo email no es correcto.");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("La contraseña no puede estar vacía.");
            return false;
        }
        return true;
    }

}

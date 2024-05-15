package com.example.behomeapp.inserter;

public class UserInserter {

    private final static String SIGNUP_QUERY = "INSERT INTO " +
            "USUARIO " +
            "(username, nombre, apellidos, email, contrasena) " +
            "VALUES (?, ?, ?, ?, ?)";


    private String txtUsername, txtName, txtLastName, txtEmail, txtPassword, txtConfirmPassword;


}

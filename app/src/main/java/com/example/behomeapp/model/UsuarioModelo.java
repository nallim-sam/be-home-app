package com.example.behomeapp.model;

public class UsuarioModelo {

    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public UsuarioModelo() {
    }

    public UsuarioModelo(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
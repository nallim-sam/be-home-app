package com.example.behomeapp.model;

import com.example.behomeapp.enums.EstanciaEnum;

import java.util.List;

public class PisoModelo {

    private String id;
    private String nombre;

    public PisoModelo() {
    }

    public PisoModelo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

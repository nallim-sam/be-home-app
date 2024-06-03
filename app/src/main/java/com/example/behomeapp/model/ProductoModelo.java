package com.example.behomeapp.model;

public class ProductoModelo {

    private int id;
    private String nombre;
    private boolean comprado;

    public ProductoModelo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.comprado = false; // Por defecto, el producto no está comprado
    }

    public ProductoModelo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }
}

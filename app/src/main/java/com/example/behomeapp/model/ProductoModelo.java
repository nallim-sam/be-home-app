package com.example.behomeapp.model;

public class ProductoModelo {

    private String id;
    private String nombre;
    private double precio;
    private boolean comprado;

    public ProductoModelo(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.comprado = false; // Por defecto, el producto no está comprado
    }

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean estaComprado() {
        return comprado;
    }

    public void marcarComoComprado() {
        this.comprado = true;
    }
}

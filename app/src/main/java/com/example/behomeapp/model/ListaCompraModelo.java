package com.example.behomeapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListaCompraModelo {

    private int id;
    private String nombre;
    private List<ProductoModelo> listProductos;

    public ListaCompraModelo() {
    }

    public ListaCompraModelo(String nombreLista) {
        this.nombre = nombreLista;
        this.listProductos = new ArrayList<>();
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

    public void setListProductos(List<ProductoModelo> listProductos) {
        this.listProductos = listProductos;
    }

    public void addProduct(ProductoModelo product) {
        listProductos.add(product);
    }

    public List<ProductoModelo> getListProductos() {
        return listProductos;
    }
}

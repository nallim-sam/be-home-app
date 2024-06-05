package com.example.behomeapp.model;

import java.util.List;

public class ListaCompraModelo {

    private int id;
    private String nombre;
    private List<ProductoModelo> productoModeloList;

    public ListaCompraModelo() {
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

    public List<ProductoModelo> getProductoModeloList() {
        return productoModeloList;
    }

    public void setProductoModeloList(List<ProductoModelo> productoModeloList) {
        this.productoModeloList = productoModeloList;
    }


}

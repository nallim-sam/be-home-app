package com.example.behomeapp.model;

import java.util.Date;

public class DataItem {
    private String nombre;
    private Date fecha;

    public DataItem(String nombre, Date fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

}

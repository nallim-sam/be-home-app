package com.example.behomeapp.model;

import java.util.Date;

public class EventoModelo {

    private int id;
    private String nombre;
    private Date fecha;
    private String horaEmpiece;
    private String horaFin;

    public EventoModelo() {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraEmpiece() {
        return horaEmpiece;
    }

    public void setHoraEmpiece(String horaEmpiece) {
        this.horaEmpiece = horaEmpiece;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}

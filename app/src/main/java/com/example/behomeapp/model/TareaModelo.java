package com.example.behomeapp.model;

import com.example.behomeapp.enums.EstanciaEnum;

import java.util.Date;

public class TareaModelo {
    private String id;
    private String nombre;
    private UsuarioModelo usuario;
    private EstanciaEnum estancia;
    private boolean completado;
    private Date fechaLimite;

    public TareaModelo() {
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

    public UsuarioModelo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModelo usuario) {
        this.usuario = usuario;
    }

    public EstanciaEnum getEstancia() {
        return estancia;
    }

    public void setEstancia(EstanciaEnum estancia) {
        this.estancia = estancia;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}

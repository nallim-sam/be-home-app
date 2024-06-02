package com.example.behomeapp.model;

import com.example.behomeapp.enums.FrecuenciaEnum;

import java.util.Date;

public class TareaModelo {
    private String id;
    private String nombre;
    private UsuarioModelo usuario;
    private PisoModelo piso;
    private boolean completado;
    private Date fechaLimite;
    private FrecuenciaEnum frecuencia;

    public TareaModelo() {
    }

    public TareaModelo(String nombre, boolean completado) {
        this.nombre = nombre;
        this.completado = completado;
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

    public PisoModelo getPiso() {
        return piso;
    }

    public void setPiso(PisoModelo piso) {
        this.piso = piso;
    }

    public FrecuenciaEnum getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaEnum frecuencia) {
        this.frecuencia = frecuencia;
    }
}

package com.example.behomeapp.model;

import com.example.behomeapp.enums.FrecuenciaEnum;


public class TareaModelo {
    private String id;
    private String nombre;
    private String idUsuario;
    private String idPiso;
    private boolean completado;
    private String fechaLimite;
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


    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public FrecuenciaEnum getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaEnum frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(String idPiso) {
        this.idPiso = idPiso;
    }
}

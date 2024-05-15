package com.example.behomeapp.model;

public class CalendarioModelo {

    private int id;
    private PisoModelo piso;
    private EventoModelo evento;

    public CalendarioModelo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PisoModelo getPiso() {
        return piso;
    }

    public void setPiso(PisoModelo piso) {
        this.piso = piso;
    }

    public EventoModelo getEvento() {
        return evento;
    }

    public void setEvento(EventoModelo evento) {
        this.evento = evento;
    }
}

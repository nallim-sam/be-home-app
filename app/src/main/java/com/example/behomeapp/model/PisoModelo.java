package com.example.behomeapp.model;

import com.example.behome.enums.EstanciaEnum;

import java.util.List;

public class PisoModelo {

    private String id;
    private String nombre;
    private List<UsuarioModelo> listaUsuarios;
    // no se si utilizar los ids o los usernames
    private List<EstanciaEnum> estanciasAsociadas;

    public PisoModelo() {
    }

    public PisoModelo(String id, String nombre, List<UsuarioModelo> listaUsuarios, List<EstanciaEnum> estanciasAsociadas) {
        this.id = id;
        this.nombre = nombre;
        this.listaUsuarios = listaUsuarios;
        this.estanciasAsociadas = estanciasAsociadas;
    }
}

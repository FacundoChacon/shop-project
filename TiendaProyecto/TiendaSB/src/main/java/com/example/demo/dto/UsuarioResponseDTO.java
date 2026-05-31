package com.example.demo.dto;

import com.example.demo.enums.Rol;

public class UsuarioResponseDTO {

    private Long id;

    private String username;

    private Rol rol;

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
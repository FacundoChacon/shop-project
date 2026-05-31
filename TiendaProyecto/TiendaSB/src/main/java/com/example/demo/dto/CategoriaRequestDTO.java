package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    //  GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

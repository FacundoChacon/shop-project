package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    //  GETTERS Y SETTERS

    public @NotBlank(message = "El nombre es obligatorio") String getNombre() {
        return nombre;
    }
    public void setNombre(@NotBlank(message = "El nombre es obligatorio") String nombre) {
        this.nombre = nombre;
    }
}

package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductRequestDTO {

    @Schema(description = "Nombre del producto", example = "Mouse Gamer")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Precio del producto", example = "15000")
    @NotNull
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Schema(description = "Cantidad de productos", example = "10")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @Schema(description = "Categoria de producto", example = "1")
    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;


    // GETTERS Y SETTERS

    public @NotBlank(message = "El nombre es obligatorio") String getNombre() {
        return nombre;
    }
    public void setNombre(@NotBlank(message = "El nombre es obligatorio") String nombre) {
        this.nombre = nombre;
    }

    public @Positive(message = "El precio debe ser mayor a 0") BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(@Positive(message = "El precio debe ser mayor a 0") BigDecimal precio) {
        this.precio = precio;
    }

    public @Min(value = 0, message = "El stock no puede ser negativo") int getStock() {
        return stock;
    }
    public void setStock(@Min(value = 0, message = "El stock no puede ser negativo") int stock) {
        this.stock = stock;
    }

    public @NotNull(message = "La categoría es obligatoria") Long getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(@NotNull(message = "La categoría es obligatoria") Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
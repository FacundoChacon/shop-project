package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

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

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
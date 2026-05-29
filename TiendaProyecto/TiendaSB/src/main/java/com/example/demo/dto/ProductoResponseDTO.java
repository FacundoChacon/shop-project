package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProductoResponseDTO {
    @Schema(description = "Identificacion del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Teclado Gamer")
    private String nombre;

    @Schema(description = "Precio del producto", example = "25000")
    private BigDecimal precio;

    @Schema(description = "Cantidad de productos", example = "15")
    private int stock;

    private CategoriaDTO categoria;

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public CategoriaDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
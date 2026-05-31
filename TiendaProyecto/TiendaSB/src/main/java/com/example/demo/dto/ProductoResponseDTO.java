package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductoResponseDTO {
    @Schema(description = "Identificacion del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Teclado Gamer")
    private String nombre;

    @Schema(description = "Precio del producto", example = "25000")
    private BigDecimal precio;

    @Schema(description = "Cantidad de productos", example = "15")
    private int stock;

    private CategoriaResponseDTO categoria;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

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

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
package com.example.demo.exception;

public class UsuarioNoEncontradoExeption extends RuntimeException {

    public UsuarioNoEncontradoExeption(
            String mensaje) {

        super(mensaje);
    }
}
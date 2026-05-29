package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // VALIDACIONES DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(
            MethodArgumentNotValidException ex){

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();

            errores.put(campo, mensaje);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }

    // ERRORES MANUALES
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> manejarNoEncontrado(
            ProductoNoEncontradoException ex){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntime(RuntimeException ex){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
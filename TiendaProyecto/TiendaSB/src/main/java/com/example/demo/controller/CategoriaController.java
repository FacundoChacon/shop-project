package com.example.demo.controller;

import com.example.demo.dto.CategoriaRequestDTO;
import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(
        name = "categorias",
        description = "Operaciones relacionadas con las categorias"
)
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    //  LISTAR TODOS
    @Operation(
            summary = "Listar Categorias",
            description = "Obtiene todas las categorias de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {return ResponseEntity.ok(service.listarCategorias());}


    //  CREAR
    @Operation(
            summary = "Crear Categoria",
            description = "Crea una categoria y la almacena en la base de datos"
    )
    @ApiResponse(responseCode = "201", description = "Categoria creada de manera correcta")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO nuevo = service.guardarCategoria(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    //  BUSCAR POR ID
    @Operation(
            summary = "Buscar categoria",
            description = "Busca una categoria especifica mediane el ID"
    )
    @ApiResponse(responseCode = "200", description = "Se ha encontrado la categoria correctamente")
    @ApiResponse(responseCode = "404", description = "No se ha encontrado la categoria")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorID(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorID(id));
    }



    //  ACTUALIZAR
    @Operation(
            summary = "Actualizar categoria",
            description = "Busca una categoria por su id y la actualiza segun lo que le pasen en el request"
    )
    @ApiResponse(responseCode = "200", description = "Categoria eliminada de manera correcta")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id,
                                                           @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarCategoria(id,dto));
    }



    //  ELIMINAR
    @Operation(
            summary = "Eliminar categoria",
            description = "Busca una categoria por id y si encuentra una coincidencia la elimina"
    )
    @ApiResponse(responseCode = "204", description = "Categoria eliminada de manera correcta")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}

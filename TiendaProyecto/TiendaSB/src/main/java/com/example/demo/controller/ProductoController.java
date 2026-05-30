package com.example.demo.controller;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(
        name = "Productos",
        description = "Operaciones relacionadas con los productos"
)
public class ProductoController {

    @Autowired
    private ProductoService service;

    // LISTAR TODOS
    @Operation(
            summary = "Listar productos",
            description = "Obtiene todos los productos de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarProductos());
    }

    // CREAR
    @Operation(
            summary = "Crear un producto",
            description = "Se le instancian los datos que van a recibir los atributos del producto y se crea en la DB"
    )
    @ApiResponse(responseCode = "201", description = "Producto guardado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> guardar(@Valid @RequestBody ProductRequestDTO dto){
        ProductoResponseDTO nuevo = service.guardarProducto(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // BUSCAR POR ID
    @Operation(
            summary = "Busca un producto por su ID",
            description = "Obtiene un producto de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ELIMINAR
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto por su ID"
    )
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // ACTUALIZAR
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza los datos de un producto existente"
    )
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody ProductRequestDTO dto){
        return ResponseEntity.ok(service.actualizarProducto(id,dto));
    }

    //  BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(
            @RequestParam String nombre){

        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    //  LISTAR PAGINADO
    @GetMapping("/pagina")
    public ResponseEntity<Page<ProductoResponseDTO>>
    listarPaginados(
            @RequestParam int pagina,
            @RequestParam int cantidad){

        return ResponseEntity.ok(
                service.listarPaginados(
                        pagina,
                        cantidad
                )
        );
    }

    //  LISTAR ORDENADOS
    @GetMapping("/ordenados")
    public ResponseEntity<List<ProductoResponseDTO>> listarOrdenados(@RequestParam String campo,
                                                                     @RequestParam String direccion){

        return ResponseEntity.ok(
                service.listarOrdenados(campo, direccion)
        );
    }

    //  LISTAR PAGINADOS Y ORDENADOS
    @GetMapping("/pagina-ordenada")
    public ResponseEntity<Page<ProductoResponseDTO>> listarPaginadosYOrdenados(@RequestParam int pagina,
                                                                               @RequestParam int cantidad,
                                                                               @RequestParam String campo,
                                                                               @RequestParam String direccion) {
        return ResponseEntity.ok(
                service.listarPaginadosYOrdenados(pagina,cantidad,campo,direccion)
        );
    }
}
package com.example.demo.service;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.exception.ProductoNoEncontradoException;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private Producto convertirAEntidad(ProductRequestDTO dto){

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        return producto;
    }

    private ProductoResponseDTO convertirAResponseDTO(Producto producto){

        ProductoResponseDTO dto = new ProductoResponseDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());

        return dto;
    }

    private ProductoResponseDTO convertirADTO(Producto producto){

        ProductoResponseDTO dto = new ProductoResponseDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());

        return dto;
    }

    @Autowired
    private ProductoRepository repository;

    // LISTAR TODOS
    public List<ProductoResponseDTO> listarProductos(){
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // GUARDAR
    public ProductoResponseDTO guardarProducto(ProductRequestDTO dto){

        Producto producto = convertirAEntidad(dto);

        Producto guardado = repository.save(producto);

        return convertirAResponseDTO(guardado);
    }

    // BUSCAR POR ID
    public ProductoResponseDTO buscarPorId(Long id){
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));
        return convertirADTO(producto);
    }

    // ELIMINAR
    public void eliminarProducto(Long id) {

        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

        repository.deleteById(id);
    }

    // ACTUALIZAR
    public ProductoResponseDTO actualizarProducto(Long id, ProductRequestDTO dto){

        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        Producto actualizado = repository.save(producto);

        return convertirAResponseDTO(actualizado);
    }
}
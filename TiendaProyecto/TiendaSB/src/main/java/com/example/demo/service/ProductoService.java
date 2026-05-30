package com.example.demo.service;

import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.exception.ProductoNoEncontradoException;
import com.example.demo.model.Categoria;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Producto convertirAEntidad(ProductRequestDTO dto){

        Producto producto = new Producto();
        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());


        return producto;
    }

    private ProductoResponseDTO convertirAResponseDTO(Producto producto){

        ProductoResponseDTO dto = new ProductoResponseDTO();
        CategoriaResponseDTO categoriaDTO;
        if(producto.getCategoria() != null){

            categoriaDTO = new CategoriaResponseDTO();

            categoriaDTO.setId(producto.getCategoria().getId());
            categoriaDTO.setNombre(producto.getCategoria().getNombre());

            dto.setCategoria(categoriaDTO);
        }

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setFechaActualizacion(producto.getFechaActualizacion());
        dto.setFechaCreacion(producto.getFechaCreacion());

        return dto;
    }

    @Autowired
    private ProductoRepository repository;

    // LISTAR TODOS
    public List<ProductoResponseDTO> listarProductos(){
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
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
        return convertirAResponseDTO(producto);
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

    //  BUSCAR POR NOMBRE
    public List<ProductoResponseDTO> buscarPorNombre(String nombre){
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    //  LISTA PAGINADA
    public Page<ProductoResponseDTO> listarProductosPaginados(
            int page,
            int size){

        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable)
                .map(this::convertirAResponseDTO);
    }

    //  LISTAR ORDENADOS
    public List<ProductoResponseDTO> listarOrdenados(
            String campo,
            String direccion){

        Sort sort;

        if(direccion.equalsIgnoreCase("desc")){
            sort = Sort.by(campo).descending();
        } else {
            sort = Sort.by(campo).ascending();
        }

        return repository.findAll(sort)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }
}
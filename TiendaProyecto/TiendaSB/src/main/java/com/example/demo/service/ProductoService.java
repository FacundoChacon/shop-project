package com.example.demo.service;

import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.enums.CampoOrdenamientoProducto;
import com.example.demo.exception.ProductoNoEncontradoException;
import com.example.demo.exception.RecursoNoEncontradoException;
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
                        new RecursoNoEncontradoException("Categoría no encontrada"));

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

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Categoría no encontrada"));

        producto.setCategoria(categoria);
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
    public Page<ProductoResponseDTO> listarPaginados(
            int pagina,
            int cantidad){

        Pageable pageable =
                PageRequest.of(pagina, cantidad);

        return repository.findAll(pageable)
                .map(this::convertirAResponseDTO);
    }

    //  LISTAR ORDENADOS
    public List<ProductoResponseDTO> listarOrdenados(
            String campo,
            String direccion){

        // VALIDAR CAMPO

        return repository.findAll(validarCampoOrdenamiento(campo,direccion))
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    //  VALIDAR CAMPO
    public Sort validarCampoOrdenamiento(String campo,String direccion) {
        CampoOrdenamientoProducto.valueOf(campo.toUpperCase());

        Sort sort =
                direccion.equalsIgnoreCase("desc")
                        ? Sort.by(campo).descending()
                        : Sort.by(campo).ascending();

        return sort;
    }

    //  LISTA PAGINADA Y ORDENADA

    public Page<ProductoResponseDTO>
    listarPaginadosYOrdenados(
            int pagina,
            int cantidad,
            String campo,
            String direccion){

        Sort sort = validarCampoOrdenamiento(campo,direccion);

        Pageable pageable =
                PageRequest.of(
                        pagina,
                        cantidad,
                        sort
                );

        return repository.findAll(pageable)
                .map(this::convertirAResponseDTO);
    }
}
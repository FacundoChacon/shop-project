package com.example.demo.service;

import com.example.demo.dto.CategoriaRequestDTO;
import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    private Categoria convertirAEntidad(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();

        categoria.setNombre(dto.getNombre());



        return categoria;
    }

    private CategoriaResponseDTO convertirAResponseDTO(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());



        return dto;
    }

    //  LISTAR CATEGORIAS
    public List<CategoriaResponseDTO> listarCategorias(){
        return repository.findAll()
                .stream().map(this::convertirAResponseDTO)
                .toList();
    }

    //  GUARDAR CATEGORIAS
    public CategoriaResponseDTO guardarCategoria(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();
        Categoria guardado = repository.save(categoria);

        return convertirAResponseDTO(guardado);
    }

    //  BUSCAR POR ID CATEGORIAS
    public CategoriaResponseDTO buscarPorID(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return convertirAResponseDTO(categoria);
    }

    //  ACTUALIZAR CATEGORIAS
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        categoria.setNombre(dto.getNombre());

        Categoria actualizado = repository.save(categoria);
        return convertirAResponseDTO(actualizado);
    }

    //  ELIMINAR CATEGORIAS
    public void eliminarCategoria(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        repository.deleteById(id);
    }

}

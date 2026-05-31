package com.example.demo.service;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //  METODOS

    private Usuario convertirAEntidad(
            UsuarioRequestDTO dto){

        Usuario usuario = new Usuario();

        usuario.setUsername(
                dto.getUsername()
        );
        usuario.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );
        usuario.setRol(
                dto.getRol()
        );

        return usuario;
    }

    private UsuarioResponseDTO convertirAResponseDTO(
            Usuario usuario){

        UsuarioResponseDTO dto =
                new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol());

        return dto;
    }

    public UsuarioResponseDTO guardarUsuario(
            UsuarioRequestDTO dto){

        Usuario usuario =
                convertirAEntidad(dto);

        Usuario guardado =
                repository.save(usuario);

        return convertirAResponseDTO(
                guardado
        );
    }
}

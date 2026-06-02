package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    //  METODOS

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>
    login(
            @RequestBody
            LoginRequestDTO dto){
        Usuario usuario =
                usuarioRepository
                        .findByUsername(
                                dto.getUsername()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuario no encontrado"
                                )
                        );
        boolean passwordCorrecta =
                passwordEncoder.matches(
                        dto.getPassword(),
                        usuario.getPassword()
                );
        if(!passwordCorrecta){
            throw new RuntimeException(
                    "Credenciales inválidas"
            );
        }
        String token =
                jwtService.generarToken(
                        usuario.getUsername()
                );
        return ResponseEntity.ok(
                new LoginResponseDTO(token)
        );
    }
}
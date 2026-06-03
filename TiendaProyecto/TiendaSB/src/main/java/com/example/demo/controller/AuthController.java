package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.enums.Rol;
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
                        usuario.getUsername(),
                        usuario.getRol().name()
                );
        return ResponseEntity.ok(
                new LoginResponseDTO(token)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequestDTO dto){

        if(usuarioRepository
                .findByUsername(dto.getUsername())
                .isPresent()){

            return ResponseEntity.badRequest()
                    .body("El usuario ya existe");
        }

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
                Rol.USER
        );

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(
                "Usuario registrado correctamente"
        );
    }
}
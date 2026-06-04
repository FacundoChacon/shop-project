package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.enums.Rol;
import com.example.demo.exception.CredencialesInvalidasException;
import com.example.demo.exception.UsuarioNoEncontradoExeption;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Autenticación",
        description = "Endpoints para login y registro de usuarios"
)
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

    @Operation(
            summary = "Iniciar sesión",
            description = "Valida las credenciales del usuario y devuelve un JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales inválidas"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    @PostMapping("/login")
    @SecurityRequirements
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
                                new UsuarioNoEncontradoExeption(
                                        "Usuario no encontrado"
                                )
                        );
        boolean passwordCorrecta =
                passwordEncoder.matches(
                        dto.getPassword(),
                        usuario.getPassword()
                );
        if(!passwordCorrecta){
            throw new CredencialesInvalidasException(
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

    @Operation(
            summary = "Registrar usuario",
            description = "Crea un nuevo usuario con rol USER"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario registrado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "El usuario ya existe"
            )
    })
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
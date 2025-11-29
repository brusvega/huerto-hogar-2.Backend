package com.example.BackEndHuerto.service;

import com.example.BackEndHuerto.dto.AuthResponse;
import com.example.BackEndHuerto.dto.LoginRequest;
import com.example.BackEndHuerto.dto.RegisterRequest;
import com.example.BackEndHuerto.model.Usuario;
import com.example.BackEndHuerto.repository.UsuarioRepository;
import com.example.BackEndHuerto.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // LOGIN ---------------------------------------------------------
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, "ROLE_" + user.getRol().toUpperCase());

    }

    // REGISTER ------------------------------------------------------
    public AuthResponse register(RegisterRequest request) {

        Usuario u = new Usuario();
        u.setNombre(request.getNombre());
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRol("CLIENTE");  // rol por defecto

        usuarioRepository.save(u);

        String token = jwtService.generateToken(u);

        return new AuthResponse(token, u.getRol());
    }
}

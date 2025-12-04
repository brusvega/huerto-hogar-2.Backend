package com.example.BackEndHuerto.security;

import com.example.BackEndHuerto.model.Usuario;
import com.example.BackEndHuerto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario u = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRol())
                .build();
    }
}

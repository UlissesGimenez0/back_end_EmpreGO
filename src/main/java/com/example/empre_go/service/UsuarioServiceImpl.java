package com.example.empre_go.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.empre_go.dto.AutenticacaoDto;
import com.example.empre_go.dto.TokenDto;

import com.example.empre_go.dto.UsuarioDto;
import com.example.empre_go.models.Usuario;
import com.example.empre_go.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public Usuario salvar(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil(dto.getPerfil());

        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDto obterUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).map(usuario -> {
            return UsuarioDto.builder()
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .perfil(usuario.getPerfil())
                    .build();
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Override
    public List<UsuarioDto> obterUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            return UsuarioDto.builder()
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .perfil(usuario.getPerfil())
                    .build();
        }).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        String[] roles = "Administrador".equals(usuario.getPerfil())
                ? new String[] { "ADMIN", "USER" }
                : new String[] { "USER" };

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    @Override
    public UserDetails autenticarTeste(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getEmail());

        boolean senhaOK = passwordEncoder.matches(
                usuario.getSenha(),
                user.getPassword()
        );

        if (senhaOK) {
            return user;
        }

        throw new RuntimeException("Senha inválida");
    }

    @Override
    public TokenDto autenticar(AutenticacaoDto autenticacao) {
        UserDetails user = loadUserByUsername(autenticacao.getEmail());

        boolean senhaOK = passwordEncoder.matches(
                autenticacao.getSenha(),
                user.getPassword()
        );

        if (senhaOK) {
            Usuario usuario = usuarioRepository.findByEmail(autenticacao.getEmail());
            String token = jwtService.gerarToken(usuario);

            return new TokenDto(usuario.getId(), autenticacao.getEmail(), token);
        }

        throw new RuntimeException("Senha inválida");
    }
}
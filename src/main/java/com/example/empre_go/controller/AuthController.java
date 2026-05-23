package com.example.empre_go.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empre_go.dto.AutenticacaoDto;
import com.example.empre_go.dto.TokenDto;
import com.example.empre_go.dto.UsuarioDto;

import com.example.empre_go.models.Usuario;
import com.example.empre_go.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public String registrar(@RequestBody UsuarioDto dto) {
        usuarioService.salvar(dto);
        return "Usuário cadastrado com sucesso!";
    }

    @PostMapping
    public TokenDto autenticar(@RequestBody AutenticacaoDto autenticacao) {
        return usuarioService.autenticar(autenticacao);
    }
}
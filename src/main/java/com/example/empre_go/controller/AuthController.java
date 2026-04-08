package com.example.empre_go.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Empregador;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.EmpregadorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CandidatoRepository candidatoRepository;
    private final EmpregadorRepository empregadorRepository;

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody Map<String, String> body) {
        if (body.get("role").equals("CANDIDATO")) {
            Candidato c = new Candidato();
            c.setNome(body.get("nome"));
            c.setEmail(body.get("email"));
            c.setSenha(body.get("senha"));
            candidatoRepository.save(c);
        } else {
            Empregador e = new Empregador();
            e.setNome(body.get("nome"));
            e.setEmail(body.get("email"));
            e.setSenha(body.get("senha"));
            empregadorRepository.save(e);
        }
        return ResponseEntity.ok("Cadastro realizado!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("senha");
        String role = body.get("role");

        if (role.equals("CANDIDATO")) {
            Candidato c = candidatoRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            if (!c.getSenha().equals(senha)) {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
            return ResponseEntity.ok("Login realizado! Id: " + c.getId());
        } else {
            Empregador e = empregadorRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            if (!e.getSenha().equals(senha)) {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
            return ResponseEntity.ok("Login realizado! Id: " + e.getId());
        }
    }
}
